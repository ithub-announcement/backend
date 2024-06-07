package ithub.announcementservice.backend.routes.drafts.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import ithub.announcementservice.backend.core.models.response.types.Response;
import ithub.announcementservice.backend.routes.drafts.services.DraftsService;
import ithub.announcementservice.backend.routes.drafts.models.DraftDTO;
import org.springframework.validation.annotation.Validated;
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
  public Response findAllDrafts(@Validated @RequestHeader String Authorization) {
    return this.draftsService.findAll(Authorization);
  }

  @GetMapping("{uuid}")
  public Response findDraftByUuid(@Validated @RequestHeader String Authorization ,@PathVariable String uuid) {
    return this.draftsService.findByUuid(Authorization, uuid);
  }

  @PostMapping("/save")
  public Response saveDraftByUUID(@Validated @RequestHeader String Authorization , @RequestParam(required = false) String uuid, @RequestBody DraftDTO body) {
    return this.draftsService.save(Authorization, uuid, body);
  }

  @DeleteMapping("/delete/{uuid}")
  public Response deleteDraftByUUID(@Validated @RequestHeader String Authorization ,@PathVariable String uuid) {
    return this.draftsService.delete(Authorization, uuid);
  }
}