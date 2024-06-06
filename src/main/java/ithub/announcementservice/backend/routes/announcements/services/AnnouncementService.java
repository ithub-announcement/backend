package ithub.announcementservice.backend.routes.announcements.services;

import ithub.announcementservice.backend.core.domain.models.AnnouncementStatus;
import ithub.announcementservice.backend.core.domain.models.entities.Announcement;
import ithub.announcementservice.backend.core.domain.repositories.AnnouncementRepository;
import ithub.announcementservice.backend.core.models.response.types.Response;
import ithub.announcementservice.backend.core.models.response.types.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * ## Сервис объявлений.
 *
 * @author Минаков Эдуард
 * */

@Slf4j
@Service
public class AnnouncementService {
  private final AnnouncementRepository repository;

  public AnnouncementService(final AnnouncementRepository repository) {
    this.repository = repository;
  }

  /**
   * Получить список объявлений.
   * */

  public Response findAll() {
    try {
      return new ResponseData<List<Announcement>>(HttpStatus.OK.value(), "found", this.repository.findByStatus(AnnouncementStatus.PUBLIC));
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

      return new ResponseData<Announcement>(HttpStatus.OK.value(), "found", announcement.get());
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error: " + err.getMessage());
    }
  }

  /**
   * Удалить объяление по UUID.
   *
   * @param uuid { UUID }
   * */

  public Response deleteByUUID(UUID uuid) {
    try {
      Optional<Announcement> announcement = this.repository.findById(uuid);

      if (announcement.isEmpty()) {
        return new Response(HttpStatus.NOT_FOUND.value(), "not found");
      }

      announcement.get().setStatus(AnnouncementStatus.ARCHIVE);
      return new Response(HttpStatus.OK.value(), "archived");
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }
}
