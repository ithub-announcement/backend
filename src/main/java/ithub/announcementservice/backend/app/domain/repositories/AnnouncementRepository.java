package ithub.announcementservice.backend.app.domain.repositories;

import ithub.announcementservice.backend.app.domain.models.AnnouncementStatus;
import ithub.announcementservice.backend.app.domain.models.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, UUID> {
  List<Announcement> findByStatus(AnnouncementStatus status);

  Optional<Announcement> findByStatusAndUuid(AnnouncementStatus status, UUID uuid);
}
