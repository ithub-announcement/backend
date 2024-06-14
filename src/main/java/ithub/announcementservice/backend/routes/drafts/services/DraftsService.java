package ithub.announcementservice.backend.routes.drafts.services;

import ithub.announcementservice.backend.core.config.Mapper;
import ithub.announcementservice.backend.core.domain.models.AnnouncementStatus;
import ithub.announcementservice.backend.core.domain.models.entities.Announcement;
import ithub.announcementservice.backend.core.domain.repositories.AnnouncementRepository;
import ithub.announcementservice.backend.core.models.response.types.Response;
import ithub.announcementservice.backend.core.models.response.types.ResponseData;
import ithub.announcementservice.backend.core.api.auth.RestClientForAuth;
import ithub.announcementservice.backend.routes.drafts.models.DraftDTO;
import ithub.announcementservice.backend.routes.review.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * ## Сервис черновиков
 *
 * @author Чехонадских Дмитрий
 * @author Горелов Дмитрий
 * */

@Service
public class DraftsService {
  private final AnnouncementRepository repository;
  private final RestClientForAuth auth;
  private final ReviewService reviewService;
  private final Mapper mapper;

  public DraftsService(final AnnouncementRepository repository, final RestClientForAuth auth, final ReviewService reviewService, final Mapper mapper) {
    this.repository = repository;
    this.auth = auth;
    this.reviewService = reviewService;
    this.mapper = mapper;
  }

  /**
   * Получить все черновики.
   * */

  public Response findAll(String Token) {
    try {
      return new ResponseData<>(
        HttpStatus.OK.value(),
        "Список получен.",
        this.repository.findByAuthorIdAndStatus(auth.getUserByToken(Token),AnnouncementStatus.DRAFT)
      );
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  /**
   * Получить черновик по UUID.
   *
   * @param uuid { String }
   * */

  public Response findByUuid(String Token, String uuid) {
    try {
      String author = auth.getUserByToken(Token);
      Optional<Announcement> current = this.repository.findByAuthorIdAndStatusAndUuid(
        author,
        AnnouncementStatus.DRAFT,
        UUID.fromString(uuid));

      if (current.isEmpty()) {
        return new Response(HttpStatus.NOT_FOUND.value(), "Черновик не найден.");
      }

      if (author.equals(current.get().getAuthorId())){
        return new ResponseData<Announcement>(HttpStatus.OK.value(), "Черновик получен.", current.get());
      }

      return new Response(HttpStatus.UNAUTHORIZED.value(),"Черновик не этого пользователя");
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  /**
   * Создать черновик.
   *
   * @param body { DraftDTO }
   * */

  private Announcement create(String author,DraftDTO body) {

    try {
      Optional<Announcement> current = Optional.ofNullable(this.mapper.getMapper().map(body, Announcement.class));

      if (current.isEmpty()) {
        return null;
      }

      current.get().setDateTime(ZonedDateTime.now(ZoneOffset.UTC));
      current.get().setStatus(AnnouncementStatus.DRAFT);
      current.get().setAuthorId(author);

      return this.repository.save(current.get());
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  /**
   * Обновить черновик по UUID.
   *
   * @param uuid { String }
   * @param body { DraftDTO }
   * */

  private Response update(String author, String uuid, DraftDTO body) {
    try {
      Optional<Announcement> current = this.repository.findByAuthorIdAndStatusAndUuid(
        author,
        AnnouncementStatus.DRAFT,
        UUID.fromString(uuid));

      if (current.isEmpty()) {
        return null;
      }

      if (!author.equals(current.get().getAuthorId())){
        return new Response(HttpStatus.UNAUTHORIZED.value(),"Черновик не этого пользователя");
      }

      current.get().setTitle(body.getTitle());
      current.get().setContent(body.getContent());
      current.get().setDateTime(ZonedDateTime.now(ZoneOffset.UTC));

      if (this.reviewService.getReview(current.get().getUuid()) != null){
        this.reviewService.deleteReview(current.get().getUuid());
      }

      this.repository.save(current.get());
      return new Response(HttpStatus.OK.value(), "Успешно сохранен");
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  /**
   * Сохранить черновик по UUID.
   * Это публичный метод, который вызывает уже создание, либо обновление черновика.
   *
   * @param uuid { String }
   * @param body { DraftDTO }
   * */

  public Response save(String Token, String uuid, DraftDTO body) {
    try {
      String author = this.auth.getUserByToken(Token);
      if (uuid != null) {
        return this.update(author,uuid, body);
      }
      return new ResponseData<Announcement>(HttpStatus.CREATED.value(), "Черновик создан.", this.create(author,body));
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  /**
   * Удалить черновик.
   *
   * @param uuid { String }
   * */

  public Response delete(String Token, String uuid) {
    try {
      String author = auth.getUserByToken(Token);
      Optional<Announcement> current = this.repository.findByAuthorIdAndStatusAndUuid(
        author,
        AnnouncementStatus.DRAFT,
        UUID.fromString(uuid));

      if (current.isEmpty()) {
        return new Response(HttpStatus.NOT_FOUND.value(), "Черновик ненайден.");
      }

      if (!author.equals(current.get().getAuthorId())) {
        return new Response(HttpStatus.UNAUTHORIZED.value(), "Черновик не этого пользователя");
      }

      if (this.reviewService.getReview(current.get().getUuid()) != null){
        this.reviewService.deleteReview(current.get().getUuid());
      }

      current.get().setStatus(AnnouncementStatus.ARCHIVE);
      this.repository.save(current.get());

      return new Response(HttpStatus.I_AM_A_TEAPOT.value(), "Удален.");
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }
}
