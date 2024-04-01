package ithub.announcementservice.backend.routes.announcements;

import ithub.announcementservice.backend.app.domain.models.AnnouncementStatus;
import ithub.announcementservice.backend.app.domain.models.entities.Announcement;
import ithub.announcementservice.backend.app.domain.repositories.AnnouncementRepository;
import ithub.announcementservice.backend.app.types.response.Response;
import ithub.announcementservice.backend.app.types.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
public class AnnouncementService {
  private final AnnouncementRepository repository;

  public AnnouncementService(final AnnouncementRepository repository) {
    this.repository = repository;
  }

  public Response findAll() {
    try {
      return new ResponseData<List<Announcement>>(HttpStatus.OK.value(), "found", this.repository.findByStatus(AnnouncementStatus.PUBLIC));
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

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