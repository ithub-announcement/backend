package ithub.announcementservice.backend.routes.drafts.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import ithub.announcementservice.backend.app.types.response.Response;
import ithub.announcementservice.backend.routes.drafts.service.DraftsService;
import ithub.announcementservice.backend.routes.drafts.models.DraftDTO;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Черновики")
@RestController
@RequestMapping("/drafts")
public class DraftsController {
  private final DraftsService draftsService;

  public DraftsController(final DraftsService draftsService) {
    this.draftsService = draftsService;
  }

  @GetMapping()
  public Response findAllDrafts() {
      return this.draftsService.findAll();

  }

  @GetMapping("{uuid}")
  public Response findDraftByUuid(@PathVariable String uuid) {
      return this.draftsService.findByUuid(uuid);
  }

  @PostMapping("/save")
  public Response saveDraftByUUID(@RequestParam(required = false) String uuid, @RequestBody DraftDTO body) {
      return this.draftsService.save(uuid, body);
  }

  @DeleteMapping("/delete/{uuid}")
  public Response deleteDraftByUUID(@PathVariable String uuid) {
      return this.draftsService.delete(uuid);
  }
}