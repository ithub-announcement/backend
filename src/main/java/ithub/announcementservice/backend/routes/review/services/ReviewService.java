package ithub.announcementservice.backend.routes.review.services;

import ithub.announcementservice.backend.core.api.auth.RestClientForAuth;
import ithub.announcementservice.backend.core.config.Mapper;
import ithub.announcementservice.backend.core.domain.models.AnnouncementStatus;
import ithub.announcementservice.backend.core.domain.models.entities.Announcement;
import ithub.announcementservice.backend.core.domain.repositories.AnnouncementRepository;
import ithub.announcementservice.backend.core.models.response.types.Response;
import ithub.announcementservice.backend.core.models.response.types.ResponseData;
import ithub.announcementservice.backend.routes.review.models.ReviewAcceptPayload;
import ithub.announcementservice.backend.routes.review.repositories.ReviewRepository;
import ithub.announcementservice.backend.routes.review.models.StatusReview;
import ithub.announcementservice.backend.routes.review.models.Review;
import ithub.announcementservice.backend.routes.tags.services.TagsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * ## Сервис модерации.
 *
 * @author Горелов Дмитрий
 * */

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
  private final AnnouncementRepository announcementRepository;
  private final ReviewRepository reviewRepository;
  private final TagsService tagsService;
  private final RestClientForAuth auth;
  private final Mapper mapper;

  /**
   * Принять на модерацию
   *
   * @param payload
   * */

  public Response acceptReview(ReviewAcceptPayload payload) {
    try {
      Announcement current = this.announcementRepository.findByStatusAndUuid(AnnouncementStatus.DRAFT,payload.getUuid()).get();

      Review review = Optional.ofNullable(this.mapper.getMapper().map(current, Review.class)).get();
      review.setStatusReview(StatusReview.review);

      review.setTags(tagsService.findByIds(payload.getTags()));
      this.reviewRepository.save(review);

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

  public Response rejectReview(UUID uuid, String reason, String token) {
    try {
      Review review = this.reviewRepository.findById(uuid).get();

      review.setStatusReview(StatusReview.reject);
      review.setReason(reason);
      review.setInspector(this.auth.getUserByToken(token));

      this.reviewRepository.save(review);
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

  public Response approveReview(UUID uuid, String token) {
    try {
      Review review = this.reviewRepository.findById(uuid).get();

      if (review.getStatusReview() == StatusReview.reject) {
        return new Response(HttpStatus.NO_CONTENT.value(), "объявление отклонено");
      }

      review.setInspector(this.auth.getUserByToken(token));
      review.setStatusReview(StatusReview.accept);
      this.reviewRepository.save(review);

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
      Optional<Review> current = this.reviewRepository.findById(uuid);
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
      return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.reviewRepository.findAllByOrderByDateTimeDesc());
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
      this.reviewRepository.deleteById(uuid);
      return new Response((HttpStatus.OK.value()), "успешно отклонено");
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  /**
   * Получить количество заявок на рассмотрении
   * */

  public Response getCountOfReview(){
    try {
      return new ResponseData(HttpStatus.OK.value(), "Успешно посчитано", this.reviewRepository.countAllByStatusReview(StatusReview.review).toString());
    }catch (Exception err){
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  /**
   * Получить все заявки на рассмотрения одного пользователя
   *
   * @param token String
   * */

  public Response getReviewByAuthor(String token){
    try{
      return new ResponseData(HttpStatus.OK.value(), "Успешно получено", reviewRepository.findAllByAuthorId(this.auth.getUserByToken(token)));
    }catch (Exception err){
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  /**
   * Получить все заявки на рассмотрения одного пользователя
   *
   * @param token String
   * */

  public Response getCountOfReviewByAuthor(String token){
    try {
      return new ResponseData(HttpStatus.OK.value(), "Успешно посчитано", this.reviewRepository.countAllByAuthorIdAndStatusReview(this.auth.getUserByToken(token), StatusReview.review));
    }catch (Exception err){
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }
}
