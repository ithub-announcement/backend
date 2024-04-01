package ithub.announcementservice.backend.routes.announcements;

import ithub.announcementservice.backend.app.domain.models.entities.Announcement;
import ithub.announcementservice.backend.app.domain.repositories.AnnouncementRepository;
import ithub.announcementservice.backend.app.types.response.Response;
import ithub.announcementservice.backend.app.types.response.ResponseData;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static ithub.announcementservice.backend.app.domain.models.AnnouncementStatus.ARCHIVE;

@Slf4j
@Service
public class AnnouncementService {

  @Autowired
  private final AnnouncementRepository _repository;

  public AnnouncementService(AnnouncementRepository repository) {
    _repository = repository;
  }

  public Response getAllAnnouncements() {
    try {
      var announcements = _repository.findAll(Sort.by(Sort.Direction.DESC, "dateTime"));
      return new ResponseData<>(HttpStatus.OK.value(), "found", announcements);
    } catch (Exception e) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error: " + e.getMessage());
    }
  }


  public Response getAnnouncementByUUID(UUID uuid) {
    try {
      Optional<Announcement> announcement = _repository.findById(uuid);
      if (announcement.isPresent()) {
        return new ResponseData<>(HttpStatus.OK.value(), "found", announcement.get());
      } else {
        return new Response(HttpStatus.NOT_FOUND.value(), "not found");
      }
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error: " + err.getMessage());
    }
  }

  public Response setAnnouncementArchive(UUID uuid) {
    try {
      Optional<Announcement> announcement = _repository.findById(uuid);
      if (announcement.isPresent()) {
        announcement.get().setStatus(ARCHIVE);
        return new Response(HttpStatus.OK.value(), "archived");
      } else {
        return new Response(HttpStatus.NOT_FOUND.value(), "not found");
      }
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error: " + err.getMessage());
    }
  }
}
