package ithub.announcementservice.backend.routes.review.models;

import ithub.announcementservice.backend.routes.tags.models.TagEntity;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ReviewDto {
  private String title;
  private String content;
  private String authorId;
  private ZonedDateTime dateTime;
  private String reason;
  private String inspector;
  private List<TagEntity> tags;
  private StatusReview statusReview;

}
