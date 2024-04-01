package ithub.announcementservice.backend.routes.tags;

import io.swagger.v3.oas.annotations.tags.Tag;
import ithub.announcementservice.backend.app.types.response.Response;
import ithub.announcementservice.backend.routes.tags.models.TagDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Категории")
@RestController
@RequestMapping("/tags")
public class TagsController {
  private final TagsService tagsService;

  public TagsController(final TagsService tagsService) {
    this.tagsService = tagsService;
  }

  @PostMapping("/create")
  public Response createTag(@RequestBody TagDTO body) {
    try {
      return this.tagsService.create(body);
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  @GetMapping()
  public Response findAllTags() {
    try {
      return this.tagsService.findAll();
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  @GetMapping("{id}")
  public Response findById(@PathVariable Long id) {
    try {
      return this.tagsService.findById(id);
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  @DeleteMapping("/delete/{id}")
  public Response deleteTagById(@PathVariable Long id) {
    try {
      return this.tagsService.deleteById(id);
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }
}
