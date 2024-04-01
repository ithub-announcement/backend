package ithub.announcementservice.backend.app.tags;

import ithub.announcementservice.backend.app.tags.models.Tages;
import ithub.announcementservice.backend.app.tags.repositories.TagsRepository;
import ithub.announcementservice.backend.app.types.response.Response;
import ithub.announcementservice.backend.app.types.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagsService {

  private final TagsRepository _repository;

  @Autowired
  public TagsService(TagsRepository tagsRepository) {
    this._repository = tagsRepository;
  }

  public Response findAll() {
    try {
      Iterable<Tages> categories = this._repository.findAll();
      return new ResponseData<>(HttpStatus.OK.value(), "все категории", categories);
    } catch (Exception e) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ошибка при получении категорий: " + e.getMessage());
    }
  }

  public Response Add(String name) {
    try {
      if (name == null || name.isEmpty()) {
        return new Response(HttpStatus.BAD_REQUEST.value(), "Не может пустовать");
      }
      Tages entity = new Tages();
      entity.setName(name);
      this._repository.save(entity);
      return new Response(HttpStatus.CREATED.value(), "Категория добавлена успешно");
    } catch (Exception e) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка при добавлении категории: " + e.getMessage());
    }
  }

  public Response delete(String id) {
    try {
      if (id == null || id.isEmpty()) {
        return new Response(HttpStatus.BAD_REQUEST.value(), "Не может пустовать");
      }
      this._repository.deleteById(id);
      return new Response(HttpStatus.OK.value(), "Категория удалена успешно");
    } catch (Exception e) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ошибка при удалении категории: " + e.getMessage());
    }
  }

  public Response update(String name, String id) {
    try {
      if (id == null || id.isEmpty() || name == null || name.isEmpty()) {
        return new Response(HttpStatus.BAD_REQUEST.value(), "ID и название категории не могут быть пустыми.");
      }
      Optional<Tages> current = this._repository.findById(id);
      if (!current.isPresent()) {
        return new Response(HttpStatus.NOT_FOUND.value(), "Категория не найдена.");
      }
      Tages category = current.get();
      category.setName(name);
      this._repository.save(category);
      return new Response(HttpStatus.OK.value(), "Категория успешно обновлена.");
    } catch (Exception e) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка при обновлении категории: " + e.getMessage());
    }
  }

  public Response findById(String id) {
    try {
      if (id == null || id.isEmpty()) {
        return new Response(HttpStatus.BAD_REQUEST.value(), "Не может пустовать");
      }
      Optional<Tages> category = this._repository.findById(id);
      if (category.isPresent()) {
        return new ResponseData<>(HttpStatus.OK.value(), "Категория найдена", category.get());
      }
      return new Response(HttpStatus.NOT_FOUND.value(), "Под эти id нет категории");
    } catch (Exception e) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка при поиске категории: " + e.getMessage());
    }
  }
}