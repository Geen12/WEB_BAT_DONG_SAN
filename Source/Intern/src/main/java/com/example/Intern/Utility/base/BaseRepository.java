package com.example.Intern.Utility.base;

import com.example.Intern.Utility.constants.Const;
import com.example.Intern.Utility.constants.QueryClause;
import com.example.Intern.Utility.constants.QueryCondition;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Repository
@SuppressWarnings("unused")
public class BaseRepository {
    @PersistenceContext
    protected EntityManager entityManager;

    /*
     * WHERE {fieldName} = :{fieldName}
     * WHERE fieldName.{keyName} = :{keyName}
     * UPDATE entityClass.getSimpleName() SET
     * {fieldName} = :{fieldName}
     */
    @Transactional
    protected int updateFieldsNotNull(Object entity) throws Exception {
        StringBuilder queryStr = new StringBuilder();
        StringBuilder condition = new StringBuilder();
        Map<String, Object> paraMap = new HashMap<>();

        Class<?> entityClass = entity.getClass();
        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            String fieldName = field.getName();
            Object fieldValue = field.get(entity);

            if (fieldValue == null) {
                continue;
            }

            if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(EmbeddedId.class)) {
                if (field.isAnnotationPresent(Id.class)) {
                    condition.append(QueryClause.WHERE).append(MessageFormat.format(QueryCondition.EQUAL, fieldName));
                    paraMap.put(fieldName, fieldValue);
                } else {
                    Class<?> compositeKey = fieldValue.getClass();
                    Field[] primaryKey = compositeKey.getDeclaredFields();

                    for (Field key : primaryKey) {
                        key.setAccessible(true);

                        if (condition.isEmpty()) {
                            condition.append(QueryClause.WHERE);
                        } else {
                            condition.append(QueryCondition.AND);
                        }

                        String keyName = key.getName();
                        Object keyValue = key.get(fieldValue);

                        condition.append(fieldName).append(Const.DOT).append(MessageFormat.format(QueryCondition.EQUAL, keyName));
                        paraMap.put(keyName, keyValue);
                    }
                }
            } else {
                if (queryStr.isEmpty()) {
                    queryStr.append(QueryClause.UPDATE).append(entityClass.getSimpleName()).append(QueryClause.SET);
                } else {
                    queryStr.append(Const.COMMA);
                }

                queryStr.append(MessageFormat.format(QueryCondition.EQUAL, fieldName));
                paraMap.put(fieldName, fieldValue);
            }
        }

        if (queryStr.isEmpty() || condition.isEmpty()) {
            return 0;
        }
        queryStr.append(condition);
        Query query = this.entityManager.createQuery(queryStr.toString());
        paraMap.forEach(query::setParameter);
        return query.executeUpdate();
    }

    @Transactional
    public int deleteByCondition(String table, String condition, Map<String, Object> paraMap) {
        String queryStr = QueryClause.DELETE + QueryClause.FROM + table + QueryClause.WHERE + condition;
        Query query = this.entityManager.createQuery(queryStr);
        paraMap.forEach(query::setParameter);
        return query.executeUpdate();
    }

    @Transactional
    public void resetIdGenerate(String tableName, String columnId) {
        String sequenceId = (String) this.entityManager
                .createNativeQuery("SELECT pg_get_serial_sequence('" +
                        tableName + "','" + columnId + "')")
                .getSingleResult();
        this.entityManager.createNativeQuery("ALTER SEQUENCE " +
                sequenceId + " RESTART WITH 1").executeUpdate();
    }
}