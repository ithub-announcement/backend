package ithub.announcementservice.backend.routes.drafts.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DraftDTO {
  private String title;
  private String content;
}
