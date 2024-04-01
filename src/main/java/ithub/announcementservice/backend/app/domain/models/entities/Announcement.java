package ithub.announcementservice.backend.app.domain.models.entities;

import ithub.announcementservice.backend.app.domain.models.AnnouncementStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "Announcement")
public class Announcement {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String authorId;

    private ZonedDateTime dateTime;

    @Enumerated
    private AnnouncementStatus status;
}
