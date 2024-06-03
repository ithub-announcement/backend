package ithub.announcementservice.backend.routes.tags.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tags")
public class TagEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String value;
  private String baseColor;
  @Column(columnDefinition = "varchar(20)")
  private String textColor;
}
