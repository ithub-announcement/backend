package ithub.announcementservice.backend.routes.tags.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import ithub.announcementservice.backend.core.models.response.types.Response;
import ithub.announcementservice.backend.routes.tags.services.TagsService;
import ithub.announcementservice.backend.routes.tags.models.TagDTO;
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
    return this.tagsService.create(body);
  }

  @GetMapping()
  public Response findAllTags() {
    return this.tagsService.findAll();
  }

  @GetMapping("{id}")
  public Response findById(@PathVariable Long id) {
    return this.tagsService.findById(id);
  }

  @DeleteMapping("/delete/{id}")
  public Response deleteTagById(@PathVariable Long id) {
    return this.tagsService.deleteById(id);
  }

  @PutMapping("/update/{id}")
  public Response updateTagById(@PathVariable Long id, @RequestBody TagDTO body) {
    return this.tagsService.updateById(id, body);
  }
}
