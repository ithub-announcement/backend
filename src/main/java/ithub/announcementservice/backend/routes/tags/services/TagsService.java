package ithub.announcementservice.backend.routes.tags.services;

import ithub.announcementservice.backend.core.config.Mapper;
import ithub.announcementservice.backend.core.models.response.types.Response;
import ithub.announcementservice.backend.core.models.response.types.ResponseData;
import ithub.announcementservice.backend.routes.tags.models.TagDTO;
import ithub.announcementservice.backend.routes.tags.models.TagEntity;
import ithub.announcementservice.backend.routes.tags.repositories.TagsRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ## Сервис категорий.
 *
 * @author Чехонадских Дмитрий
 * @author Таранов Владислав
 * */

@Service
public class TagsService {
  private final TagsRepository repository;
  private final Mapper mapper;

  public TagsService(final TagsRepository tagsRepository, Mapper mapper) {
    this.repository = tagsRepository;
    this.mapper = mapper;
  }

  /**
   * Создать категорию.
   *
   * @param body { TagDTO }
   * */

  public Response create(@Valid TagDTO body) {
    try {
      Optional<TagEntity> current = Optional.ofNullable(this.mapper.getMapper().map(body, TagEntity.class));
      return current.map(tagEntity -> new ResponseData<>(HttpStatus.CREATED.value(), "Успешно создан.", this.repository.save(tagEntity))).orElse(null);
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  /**
   * Получить все категории.
   * */

  public Response findAll() {
    try {
      return new ResponseData<List<TagEntity>>(HttpStatus.OK.value(), "Success", this.repository.findAll());
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  /**
   * Получить категорию по ID.
   *
   * @param id { Long }
   * */

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

  /**
   * Удалить категорию.
   *
   * @param id { Long }
   * */

  public Response deleteById(Long id) {
    try {
      this.repository.deleteById(id);
      return new Response(HttpStatus.I_AM_A_TEAPOT.value(), "deleted");
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }

  /**
   * Изменить категорию.
   *
   * @param id { Long }
   * @param body { TagDTO }
   * */

  public Response updateById(Long id, @Valid TagDTO body) {
    try {
      Optional<TagEntity> existingTag = this.repository.findById(id);

      if (existingTag.isEmpty()) {
        return new Response(HttpStatus.NOT_FOUND.value(), "Нету");
      }

      TagEntity tagEntity = existingTag.get();
      this.mapper.getMapper().map(body, tagEntity);
      TagEntity updatedTag = this.repository.save(tagEntity);

      return new ResponseData<>(HttpStatus.OK.value(), "Tag изменен", updatedTag);
    } catch (Exception err) {
      throw new RuntimeException(err);
    }
  }


  /**
   * Получить список категорий по ID.
   *
   * @param ids { Long[] }
   * */

  public List<TagEntity> findByIds(List<Long> ids) {
    try {
      return this.repository.findAllById(ids);
    } catch (Exception err){
      throw new RuntimeException(err);
    }
  }
}
