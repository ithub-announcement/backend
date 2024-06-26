package ithub.announcementservice.backend.core.domain.models.entities;

import ithub.announcementservice.backend.core.domain.models.AnnouncementStatus;
import ithub.announcementservice.backend.routes.tags.models.TagEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table()
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

  private AnnouncementStatus status;

  @ManyToMany
  private List<TagEntity> tags;
}
