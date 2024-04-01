package ithub.announcementservice.backend.app.domain.repositories;

import ithub.announcementservice.backend.app.domain.models.entities.Announcement;
import ithub.announcementservice.backend.app.domain.models.AnnouncementStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AnnouncementRepository extends JpaRepository<Announcement, UUID> {
  List<Announcement> findByStatus(AnnouncementStatus status);

  Announcement findByStatusAndUuid(AnnouncementStatus status, UUID uuid);
}
