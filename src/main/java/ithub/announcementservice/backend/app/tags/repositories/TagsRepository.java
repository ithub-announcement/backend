package ithub.announcementservice.backend.app.tags.repositories;

import ithub.announcementservice.backend.app.tags.models.Tages;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TagsRepository extends JpaRepository<Tages, String> {
}
