package ithub.announcementservice.backend.routes.review.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ReviewAcceptPayload {
  public UUID uuid;
  public List<Long> tags;
}
