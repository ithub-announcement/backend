package ithub.announcementservice.backend.routes.review.models;

import ithub.announcementservice.backend.routes.tags.models.TagEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "review")
public class Review {
  @Id
  @Column(unique = true, nullable = false, updatable = false)
  private UUID uuid;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String content;

  private String authorId;

  private ZonedDateTime dateTime;

  private String reason;

  private String inspector;

  @ManyToMany
  private List<TagEntity> tags;

  private StatusReview statusReview;
}
