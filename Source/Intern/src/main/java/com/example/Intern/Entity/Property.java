package com.example.Intern.Entity;

import com.example.Intern.Utility.Enum.STATUS_VERIFICATION;
import com.example.Intern.Utility.constants.ColumnName;
import com.example.Intern.Utility.constants.TableName;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = TableName.PROPERTIES)
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ColumnName.Properties.ID)
    private Long id;

    @ManyToOne
    @JoinColumn(name = ColumnName.Properties.USER_ID, nullable = false)
    private User user;

    @Column(name = ColumnName.Properties.TITLE, nullable = false)
    private String title;

    @Column(name = ColumnName.Properties.DESCRIPTION, nullable = false)
    private String description;

    @Column(name = ColumnName.Properties.PRICE, nullable = false)
    private Double price;

    @Column(name = ColumnName.Properties.AREA, nullable = false)
    private Double area;

    @Column(name = ColumnName.Properties.LOCATION, nullable = false)
    private String location;

    @Column(name = ColumnName.Properties.IMAGE_URL)
    private String imageUrl;

    @ElementCollection
    @CollectionTable(name = "property_images", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "image_url")
    private List<String> images;  // Lưu danh sách các ảnh bất động sản

    @Enumerated(EnumType.STRING)
    @Column(name = ColumnName.Properties.VERIFICATION_STATUS, nullable = false)
    private STATUS_VERIFICATION verificationStatus;

    @Column(name = ColumnName.Properties.CREATE_AT, updatable = false)
    private Timestamp createAt;

    @Column(name = ColumnName.Properties.UPDATE_AT)
    private Timestamp updateAt;

    @PrePersist
    protected void onCreate() {
        this.createAt = new Timestamp(System.currentTimeMillis());
        if (this.verificationStatus == null) {
            this.verificationStatus = STATUS_VERIFICATION.WAIT;  // Default to PENDING if not set
        }
        this.createAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = new Timestamp(System.currentTimeMillis());
    }

    // Add the setter for imageUrls
    public void setImageUrls(List<String> imageUrls) {
        this.images = imageUrls;
    }

    // Method to set image URL after saving
    public void setImageUrlAfterSave() {
        if (this.id != null) {
            this.imageUrl = "/photo/property/" + this.id + "/1.jpg";
        }
    }
}