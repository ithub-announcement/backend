package ithub.announcementservice.backend.routes.review.services;

import ithub.announcementservice.backend.core.config.Mapper;
import ithub.announcementservice.backend.core.domain.models.AnnouncementStatus;
import ithub.announcementservice.backend.core.domain.models.entities.Announcement;
import ithub.announcementservice.backend.core.domain.repositories.AnnouncementRepository;
import ithub.announcementservice.backend.core.models.response.types.Response;
import ithub.announcementservice.backend.core.models.response.types.ResponseData;
import ithub.announcementservice.backend.routes.review.repositories.ReviewRepository;
import ithub.announcementservice.backend.routes.review.models.StatusReview;
import ithub.announcementservice.backend.routes.review.models.Review;
import ithub.announcementservice.backend.routes.tags.services.TagsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService {
  private final AnnouncementRepository announcementRepository;
  private final ReviewRepository reviewRepository;
  private final TagsService tagsService;
  private final Mapper mapper;

  public ReviewService(final AnnouncementRepository repository, ReviewRepository reviewRepository, TagsService tagsService, final Mapper mapper) {
    this.announcementRepository = repository;
    this.reviewRepository = reviewRepository;
    this.tagsService = tagsService;
    this.mapper = mapper;
  }

  /**
   * Принять на модерацию
   *
   * @param uuid UUID
   * @param tags List<TagEntity>
   * */

  public Response acceptReview(UUID uuid, List<Long> tags) {
    try {
      Announcement current = announcementRepository.findByStatusAndUuid(AnnouncementStatus.DRAFT,uuid).get();
      Review review = Optional.ofNullable(this.mapper.getMapper().map(current, Review.class)).get();

      review.setTags(tagsService.findByIds(tags));
      reviewRepository.save(review);

      return new Response(HttpStatus.OK.value(), "Успешно принят");
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  /**
   * Отклонить публикацию
   *
   * @param uuid UUID
   * @param reason String
   * */

  public Response rejectReview(UUID uuid, String reason) {
    try {
      Review review = reviewRepository.findById(uuid).get();

      review.setStatusReview(StatusReview.reject);
      review.setReason(reason);

      reviewRepository.save(review);
      return new Response(HttpStatus.OK.value(), "Успешно отклонена");
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  /**
   * Одобрить публикацию
   *
   * @param uuid UUID
   * */

  public Response approveReview(UUID uuid) {
    try {
      Review review = reviewRepository.findById(uuid).get();

      if (review.getStatusReview() == StatusReview.reject) {
        return new Response(HttpStatus.NO_CONTENT.value(), "объявление отклонено");
      }

      review.setStatusReview(StatusReview.accept);
      reviewRepository.save(review);

      return new Response(HttpStatus.OK.value(), "Успешно одобрена");
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  /**
   * Получить одну публикацию
   *
   * @param uuid UUID
   * */

  public Response getReview(UUID uuid) {
    try {
      Optional<Review> current = reviewRepository.findById(uuid);
      return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", current.get());
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  /**
   * Получить все публикации находящиеся на модерации
   * */

  public Response getReviews() {
    try {
      return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", reviewRepository.findAll());
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  /**
   * Удалить публикацию
   *
   * @param uuid UUID
   * */

  public Response deleteReview(UUID uuid) {
    try {
      reviewRepository.deleteById(uuid);
      return new Response((HttpStatus.OK.value()), "успешно отклонено");
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }
}
