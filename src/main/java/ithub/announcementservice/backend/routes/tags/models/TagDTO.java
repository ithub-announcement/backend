package ithub.announcementservice.backend.routes.tags.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
  private String value;
  private String baseColor;
}
