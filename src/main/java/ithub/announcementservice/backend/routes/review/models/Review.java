package ithub.announcementservice.backend.routes.review.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "review")
public class Review {
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

  private String comments;

  @ElementCollection
  private List<Integer> tags;

  private StatusReview statusReview;
}
