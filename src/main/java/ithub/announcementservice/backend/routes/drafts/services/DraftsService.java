package ithub.announcementservice.backend.routes.drafts.services;

import ithub.announcementservice.backend.core.config.Mapper;
import ithub.announcementservice.backend.core.domain.models.AnnouncementStatus;
import ithub.announcementservice.backend.core.domain.models.entities.Announcement;
import ithub.announcementservice.backend.core.domain.repositories.AnnouncementRepository;
import ithub.announcementservice.backend.core.models.response.types.Response;
import ithub.announcementservice.backend.core.models.response.types.ResponseData;
import ithub.announcementservice.backend.routes.auth.RestClientForAuth;
import ithub.announcementservice.backend.routes.drafts.models.DraftDTO;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DraftsService {
  private final AnnouncementRepository repository;
  private final RestClientForAuth auth;
  private final Mapper mapper;

  public DraftsService(final AnnouncementRepository repository, RestClientForAuth auth, final Mapper mapper) {
    this.repository = repository;
    this.auth = auth;
    this.mapper = mapper;
  }

  public Response findAll(String Token) {
    try {
      return new ResponseData<List<Announcement>>(
        HttpStatus.OK.value(),
        "Список получен.",
        this.repository.findAnnouncementByAuthorIdAndAndStatus(auth.getUserByToken(Token),AnnouncementStatus.DRAFT)
      );
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  public Response findByUuid(String Token, String uuid) {
    try {
      Optional<Announcement> current = this.repository.findAnnouncementByAuthorIdAndStatusAndUuid(
        auth.getUserByToken(Token),
        AnnouncementStatus.DRAFT,
        UUID.fromString(uuid));

      if (current.isEmpty()) {
        return new Response(HttpStatus.NOT_FOUND.value(), "Черновик не найден.");
      }

      return new ResponseData<Announcement>(HttpStatus.OK.value(), "Черновик получен.", current.get());

    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

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

  private Announcement update(String author, String uuid, DraftDTO body) {
    try {
      Optional<Announcement> current = this.repository.findAnnouncementByAuthorIdAndStatusAndUuid(
        author,
        AnnouncementStatus.DRAFT,
        UUID.fromString(uuid));

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

  public Response save(String Token, String uuid, DraftDTO body) {
    try {
      String author = this.auth.getUserByToken(Token);
      if (uuid != null) {
        return new ResponseData<Announcement>(HttpStatus.ACCEPTED.value(), "Черновик изменен.", this.update(author,uuid, body));
      }
      return new ResponseData<Announcement>(HttpStatus.CREATED.value(), "Черновик создан.", this.create(author,body));
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  public Response delete(String Token, String uuid) {
    try {
      Optional<Announcement> current = this.repository.findAnnouncementByAuthorIdAndStatusAndUuid(
        auth.getUserByToken(Token),
        AnnouncementStatus.DRAFT,
        UUID.fromString(uuid));

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