package ithub.announcementservice.backend.routes.tags.repositories;

import ithub.announcementservice.backend.routes.tags.models.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends JpaRepository<TagEntity, Long> {
}
