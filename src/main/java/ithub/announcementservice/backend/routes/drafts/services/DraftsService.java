package ithub.announcementservice.backend.routes.drafts.services;

import ithub.announcementservice.backend.core.config.Mapper;
import ithub.announcementservice.backend.core.domain.models.AnnouncementStatus;
import ithub.announcementservice.backend.core.domain.models.entities.Announcement;
import ithub.announcementservice.backend.core.domain.repositories.AnnouncementRepository;
import ithub.announcementservice.backend.core.models.response.types.Response;
import ithub.announcementservice.backend.core.models.response.types.ResponseData;
import ithub.announcementservice.backend.routes.drafts.models.DraftDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Slf4j
@Service
public class DraftsService {
  private final AnnouncementRepository repository;
  private final Mapper mapper;

  public DraftsService(final AnnouncementRepository repository, final Mapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public Response findAll() {
    try {
      List<Announcement> announcementList = repository.findAllByStatusOrderByDateTimeDesc(AnnouncementStatus.DRAFT);
      ZonedDateTime thirtyDaysAgo = ZonedDateTime.now().minusDays(30);
      for (Announcement announcement : announcementList) {
        if (announcement.getDateTime().isBefore(thirtyDaysAgo)) {
          announcement.setStatus(AnnouncementStatus.ARCHIVE);
          log.info(announcement.getUuid() + ": was archived!" );
          repository.save(announcement);
        }
      }

      announcementList = announcementList.stream()
        .filter(announcement -> announcement.getStatus().equals(AnnouncementStatus.DRAFT))
        .collect(Collectors.toList());

      return new ResponseData<>(
        HttpStatus.OK.value(),
        "Список получен.", announcementList);
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  public Response findByUuid(String uuid) {
    try {
      Optional<Announcement> current = this.repository.findByStatusAndUuid(AnnouncementStatus.DRAFT, UUID.fromString(uuid));

      if (current.isEmpty()) {
        return new Response(HttpStatus.NOT_FOUND.value(), "Черновик не найден.");
      }

      return new ResponseData<Announcement>(HttpStatus.OK.value(), "Черновик получен.", current.get());

    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  private Announcement create(DraftDTO body) {
    try {
      Optional<Announcement> current = Optional.ofNullable(this.mapper.getMapper().map(body, Announcement.class));

      if (current.isEmpty()) {
        return null;
      }

      current.get().setDateTime(ZonedDateTime.now(ZoneOffset.UTC));
      current.get().setStatus(AnnouncementStatus.DRAFT);

      return this.repository.save(current.get());
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  private Announcement update(String uuid, DraftDTO body) {
    try {
      Optional<Announcement> current = this.repository.findByStatusAndUuid(AnnouncementStatus.DRAFT, UUID.fromString(uuid));

      if (current.isEmpty()) {
        return null;
      }

      current.get().setTitle(body.getTitle());
      current.get().setContent(body.getContent());
      current.get().setDateTime(ZonedDateTime.now(ZoneOffset.UTC));

      return this.repository.save(current.get());
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  public Response save(String uuid, DraftDTO body) {
    try {
      if (uuid != null)
        return new ResponseData<Announcement>(HttpStatus.ACCEPTED.value(), "Черновик изменен.", this.update(uuid, body));
      return new ResponseData<Announcement>(HttpStatus.CREATED.value(), "Черновик создан.", this.create(body));
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  public Response delete(String uuid) {
    try {
      Optional<Announcement> current = this.repository.findByStatusAndUuid(AnnouncementStatus.DRAFT, UUID.fromString(uuid));

      if (current.isEmpty()) {
        return new Response(HttpStatus.NOT_FOUND.value(), "Черновик ненайден.");
      }

      current.get().setStatus(AnnouncementStatus.ARCHIVE);

      this.repository.save(current.get());

      return new Response(HttpStatus.I_AM_A_TEAPOT.value(), "Удален.");
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }
}