package ithub.announcementservice.backend.app.domain.repositories;

import ithub.announcementservice.backend.app.domain.models.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnnouncementRepository extends JpaRepository<Announcement, UUID> {
}
