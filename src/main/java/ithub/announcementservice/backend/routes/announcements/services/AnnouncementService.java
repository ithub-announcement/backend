package ithub.announcementservice.backend.routes.announcements.services;

import ithub.announcementservice.backend.core.api.auth.RestClientForAuth;
import ithub.announcementservice.backend.core.api.auth.models.User;
import ithub.announcementservice.backend.core.domain.models.AnnouncementStatus;
import ithub.announcementservice.backend.core.domain.models.entities.Announcement;
import ithub.announcementservice.backend.core.domain.repositories.AnnouncementRepository;
import ithub.announcementservice.backend.core.models.response.types.Response;
import ithub.announcementservice.backend.core.models.response.types.ResponseData;
import ithub.announcementservice.backend.routes.review.models.Review;
import ithub.announcementservice.backend.routes.review.repositories.ReviewRepository;
import ithub.announcementservice.backend.routes.review.services.ReviewService;
import ithub.announcementservice.backend.routes.tags.models.TagEntity;
import ithub.announcementservice.backend.routes.tags.services.TagsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * ## Сервис объявлений.
 *
 * @author Минаков Эдуард
 * @author Горелов Дмитрий
 * */

@Slf4j
@Service
@RequiredArgsConstructor
public class  AnnouncementService {
  private final AnnouncementRepository repository;

  private final RestClientForAuth auth;

  private final ReviewService reviewService;

  private final ReviewRepository reviewRepository;

  private final TagsService tagsService;

  /**
   * Получить объявления по тэгам
   * */

  public Response findByTags(List<TagEntity> tags){
    try {
      if (tags.isEmpty()){
        return new Response(HttpStatus.BAD_REQUEST.value(), "Лист тэгов пустой");
      }
      return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", repository.findAnnouncementByStatusAndTagsIn(AnnouncementStatus.PUBLIC, tags));
    }catch (Exception err){
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  /**
   * Отправить на публикацию
   * */

  public Response sendToPublication(UUID uuid, String token){
    try{
      User user = auth.getRoleAndUserByToken(token);
      Announcement current = repository.findByAuthorIdAndStatusAndUuid(user.getUid(), AnnouncementStatus.DRAFT,uuid).get();

      if (current.getStatus() != AnnouncementStatus.DRAFT){
        return new Response(HttpStatus.NO_CONTENT.value(), "Объявление не находится в черновиках");
      }

      reviewService.approveReview(uuid, token);

      List<Long> currentList = new ArrayList<>();
      Review review = reviewRepository.findById(uuid).get();

      for (int i = 0; i < review.getTags().size(); i++){
        currentList.add(review.getTags().get(i).getId());
      }

      current.setTags(tagsService.findByIds(currentList));
      current.setStatus(AnnouncementStatus.PUBLIC);
      repository.save(current);

      return new Response(HttpStatus.OK.value(), "Успешно добавлено");
    }catch (Exception err){
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  /**
   * Получить список объявлений.
   * */

  public Response findAll() {
    try {
      return new ResponseData<>(HttpStatus.OK.value(), "found", this.repository.findAllByStatusOrderByDateTimeDesc(AnnouncementStatus.PUBLIC));
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  /**
   * Получить объявление по UUID.
   *
   * @param uuid { UUID }
   * */

  public Response findByUUID(UUID uuid) {
    try {
      Optional<Announcement> announcement = this.repository.findById(uuid);

      if (announcement.isEmpty()) {
        return new Response(HttpStatus.NOT_FOUND.value(), "not found");
      }

      return new ResponseData<>(HttpStatus.OK.value(), "found", announcement.get());
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error: " + err.getMessage());
    }
  }

  /**
   * Удалить объяление по UUID.
   *
   * @param uuid { UUID }
   * */

  public Response deleteByUUID(UUID uuid, String token) {
    try {
      Announcement announcement = this.repository.findById(uuid).get();

      if (announcement == null) {
        return new Response(HttpStatus.NOT_FOUND.value(), "not found");
      }

      User deleted = auth.getRoleAndUserByToken(token);

      if (!announcement.getAuthorId().equals(deleted.getUid()) && deleted.getRole() != "ADMIN"){
        return new Response(HttpStatus.NOT_FOUND.value(), "Не явлеятся автором объявления");
      }

      announcement.setStatus(AnnouncementStatus.ARCHIVE);
      this.repository.save(announcement);
      return new Response(HttpStatus.I_AM_A_TEAPOT.value(), "ARCHIVE");
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }
}