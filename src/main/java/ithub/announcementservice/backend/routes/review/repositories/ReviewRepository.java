package ithub.announcementservice.backend.routes.review.repositories;

import ithub.announcementservice.backend.routes.review.models.Review;
import ithub.announcementservice.backend.routes.review.models.StatusReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
  Long countAllByStatusReview(StatusReview statusReview);

  List<Review> findAllByAuthorId(String authorId);

  Long countAllByAuthorIdAndStatusReview(String authorId, StatusReview statusReview);
}
