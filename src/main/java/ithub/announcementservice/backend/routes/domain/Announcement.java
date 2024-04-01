package ithub.announcementservice.backend.routes.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "Announcement")
public class Announcement {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid", length = 36, unique = true, nullable = false, updatable = false)
    private UUID id;

}
