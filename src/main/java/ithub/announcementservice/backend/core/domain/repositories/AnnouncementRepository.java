package ithub.announcementservice.backend.core.domain.repositories;

import ithub.announcementservice.backend.core.domain.models.AnnouncementStatus;
import ithub.announcementservice.backend.core.domain.models.entities.Announcement;
import ithub.announcementservice.backend.routes.tags.models.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, UUID> {
  List<Announcement> findAllByStatusOrderByDateTimeDesc(AnnouncementStatus status);

  Optional<Announcement> findByStatusAndUuid(AnnouncementStatus status, UUID uuid);

  List<Announcement> findAllByAuthorIdAndStatusOrderByDateTimeDesc(String authorId, AnnouncementStatus status);

  Optional<Announcement> findByAuthorIdAndStatusAndUuid(String authorId, AnnouncementStatus status, UUID uuid);

  List<Announcement> findAnnouncementByStatusAndTagsIn(AnnouncementStatus status, Collection<TagEntity> tags);

}
