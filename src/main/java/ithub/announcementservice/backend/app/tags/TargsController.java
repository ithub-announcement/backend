package ithub.announcementservice.backend.app.tags;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ithub.announcementservice.backend.app.types.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Теги", description = "Сервис тегов")
@RestController
@RequestMapping("/Tegs")
public class TargsController {
  private final TagsService _tagsService;

  public TargsController(TagsService tagsService) {
    this._tagsService = tagsService;
  }

  @Operation(summary = "Добавить тег")
  @PostMapping("/add")
  public Response AddTag(@RequestBody String name) {
    try {
      return _tagsService.Add(name);
    } catch (Exception error) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка при добавлении тегов: " + error.getMessage());
    }
  }

  @DeleteMapping("/delete/{id}")
  @Operation(summary = "удалить тег")
  public Response deleteTag(@PathVariable String id) {
    try {
      return _tagsService.delete(id);
    } catch (Exception error) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка при удалении тегов: " + error.getMessage());
    }
  }

  @Operation(summary = "Получить все Теги")
  @GetMapping("/all")
  public Response getallTag() {
    try {
      return _tagsService.findAll();
    } catch (Exception error) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка при получении всех тегов: " + error.getMessage());
    }
  }

  @Operation(summary = "Получить один тег по  id")
  @GetMapping("/{id}")
  public Response getTagid(@PathVariable String id) {
    try {
      return _tagsService.findById(id);
    } catch (Exception error) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка при получении тега по id: " + error.getMessage());
    }
  }

  @PutMapping("/update/{id}")
  @Operation(summary = "Обновить тег")
  public Response UpdateTagById(@RequestBody String name, @PathVariable String id) {
    try {
      return _tagsService.update(name, id);
    } catch (Exception error) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка при обновлении тега: " + error.getMessage());
    }
  }
}