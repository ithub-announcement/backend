package ithub.announcementservice.backend.routes.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "AnnouncementBlock")
public class AnnouncementBlock {
    @Id
    private long id;

    @Column(name = "AnnouncementBlockType")
    private AnnouncementBlockType type;

    @Column(name = "data")
    private String data;
}
