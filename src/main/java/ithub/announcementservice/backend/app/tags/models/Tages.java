package ithub.announcementservice.backend.app.tags.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tages")
public class Tages {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "Name")
  String name;
  @Column( name = "BaseColor")
  String BaseColor;
}