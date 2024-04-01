package ithub.announcementservice.backend.app.domain.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity()
public class AnnouncementBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated
    private AnnouncementBlockType type;

    private String data;

    @ManyToOne()
    @JoinColumn()
    private Announcement announcement;
}
