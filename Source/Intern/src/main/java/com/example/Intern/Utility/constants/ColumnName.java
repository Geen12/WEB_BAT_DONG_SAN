package com.example.Intern.Utility.constants;

@SuppressWarnings("unused")
public class ColumnName {
    public static final class Users {
        public static final String ID = "id";
        public static final String FULL_NAME = "full_name";
        public static final String EMAIL = "email";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String ADDRESS = "address";
        public static final String DATE_OF_BIRTH = "date_of_birth";
        public static final String GENDER = "gender";
        public static final String USER_NAME = "user_name";
        public static final String PASSWORD = "password";
        public static final String STATUS = "status";
        public static final String ACCEPT_TERMS = "accept_terms";
        public static final String CREATE_AT = "create_at";
        public static final String UPDATE_AT = "update_at";

        public static final String ROLE = "role";
    }

    public static final class Token {
        public static final String ID = "id";
        public static final String USER_ID = "user_id";
        public static final String TOKEN = "token";
        public static final String CREATE_AT = "create_at";
    }

    public static final class Role {
        public static final String ID = "id";
        public static final String ROLE = "role";
        public static final String USER_ID = "user_id";
    }

    public static final class Properties {
        public static final String ID = "id";
        public static final String USER_ID = "user_id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String PRICE = "price";
        public static final String AREA = "area";
        public static final String LOCATION = "location";
        public static final String IMAGE_URL = "image_url";
        public static final String VERIFICATION_STATUS = "verification_status";
        public static final String CREATE_AT = "create_at";
        public static final String UPDATE_AT = "update_at";
    }

    public static final class Transactions {
        public static final String ID = "id";
        public static final String BUYER_ID = "buyer_id";
        public static final String PROPERTY_ID = "property_id";
        public static final String TRANSACTION_DATE = "transaction_date";
        public static final String TRANSACTION_AMOUNT = "transaction_amount";
        public static final String STATUS = "status";
    }

    public static final class LegalSupport {
        public static final String ID = "id";
        public static final String USER_ID = "user_id";
        public static final String BUYER_ID = "buyer_id";
        public static final String PROPERTY_ID = "property_id";
        public static final String SERVICE_TYPE = "service_type";
        public static final String STATUS = "status";
        public static final String CREATE_AT = "create_at";
        public static final String UPDATE_AT = "update_at";
    }

    public static final class Verifications {
        public static final String ID = "id";
        public static final String PROPERTY_ID = "property_id";
        public static final String VERIFIER_ID = "verifier_id";
        public static final String VERIFICATION_STATUS = "verification_status";
        public static final String NOTES = "notes";
        public static final String VERIFICATION_DATE = "verification_date";
    }

    public static final class Payments {
        public static final String ID = "id";
        public static final String USER_ID = "user_id";
        public static final String TRANSACTION_ID = "transaction_id";
        public static final String PAYMENT_METHOD = "payment_method";
        public static final String PAYMENT_AMOUNT = "payment_amount";
        public static final String PAYMENT_STATUS = "payment_status";
        public static final String PAYMENT_DATE = "payment_date";
    }


}