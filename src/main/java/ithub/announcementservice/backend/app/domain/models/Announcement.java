package ithub.announcementservice.backend.app.domain.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "Announcement")
public class Announcement {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private UUID uuid;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(cascade = CascadeType.MERGE)
    private List<AnnouncementBlock> content;

    private String authorId;

    @Enumerated
    private AnnouncementStatus status;
}
