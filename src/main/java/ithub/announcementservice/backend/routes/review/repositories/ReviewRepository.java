package ithub.announcementservice.backend.routes.review.repositories;

import ithub.announcementservice.backend.routes.review.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
}
