package ithub.announcementservice.backend.routes.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CollectionIdJdbcTypeCode;

@Data
@Entity
@Table(name = "Categories")
public class Categories {
    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String baseColor;
}
