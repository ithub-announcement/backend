package ithub.announcementservice.backend.routes.tags;

import ithub.announcementservice.backend.app.config.Mapper;
import ithub.announcementservice.backend.app.types.response.Response;
import ithub.announcementservice.backend.app.types.response.ResponseData;
import ithub.announcementservice.backend.routes.tags.models.TagDTO;
import ithub.announcementservice.backend.routes.tags.models.TagEntity;
import ithub.announcementservice.backend.routes.tags.repositories.TagsRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagsService {
  private final TagsRepository repository;
  private final Mapper mapper;

  public TagsService(final TagsRepository tagsRepository, Mapper mapper) {
    this.repository = tagsRepository;
    this.mapper = mapper;
  }

  public Response create(@Valid TagDTO body) {
    try {
      Optional<TagEntity> current = Optional.ofNullable(this.mapper.getMapper().map(body, TagEntity.class));
      return current.map(tagEntity -> new ResponseData<>(HttpStatus.CREATED.value(), "Успешно создан.", this.repository.save(tagEntity))).orElse(null);
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  public Response findAll() {
    try {
      return new ResponseData<List<TagEntity>>(HttpStatus.OK.value(), "Success", this.repository.findAll());
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  public Response findById(Long id) {
    try {
      Optional<TagEntity> current = this.repository.findById(id);

      if (current.isEmpty()) {
        return new Response(HttpStatus.NOT_FOUND.value(), "not found.");
      }

      return new ResponseData<TagEntity>(HttpStatus.OK.value(), "Success", current.get());
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  public Response deleteById(Long id) {
    try {
      this.repository.deleteById(id);
      return new Response(HttpStatus.I_AM_A_TEAPOT.value(), "deleted");
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }
}
