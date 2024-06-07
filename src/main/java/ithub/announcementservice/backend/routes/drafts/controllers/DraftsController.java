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
  public Response findAllDrafts(@Validated @RequestHeader String token) {
    return this.draftsService.findAll(token);
  }

  @GetMapping("{uuid}")
  public Response findDraftByUuid(@Validated @RequestHeader String token ,@PathVariable String uuid) {
    return this.draftsService.findByUuid(token, uuid);
  }

  @PostMapping("/save")
  public Response saveDraftByUUID(@Validated @RequestHeader String token , @RequestParam(required = false) String uuid, @RequestBody DraftDTO body) {
    return this.draftsService.save(token, uuid, body);
  }

  @DeleteMapping("/delete/{uuid}")
  public Response deleteDraftByUUID(@Validated @RequestHeader String token ,@PathVariable String uuid) {
    return this.draftsService.delete(token, uuid);
  }
}