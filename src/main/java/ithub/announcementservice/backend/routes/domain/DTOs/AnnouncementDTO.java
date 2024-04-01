package ithub.announcementservice.backend.routes.domain.DTOs;

import ithub.announcementservice.backend.routes.domain.AnnouncementBlock;
import ithub.announcementservice.backend.routes.domain.AnnouncementStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class AnnouncementDTO {
    @Column(name = "tittle")
    private String tittle;

    @OneToMany(mappedBy = "Announcement")
    private List<AnnouncementBlock> content;

    @Column(name = "author")
    private String authorID;

    @Column(name = "Status")
    private AnnouncementStatus status;
}
