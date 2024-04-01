package ithub.announcementservice.backend.routes.drafts;

import io.swagger.v3.oas.annotations.tags.Tag;
import ithub.announcementservice.backend.app.types.response.Response;
import ithub.announcementservice.backend.routes.drafts.models.DraftDTO;
import org.springframework.http.HttpStatus;
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
    try {
      return this.draftsService.findAll();
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  @GetMapping("{uuid}")
  public Response findDraftByUuid(@PathVariable String uuid) {
    try {
      return this.draftsService.findByUuid(uuid);
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  @PostMapping("/save")
  public Response saveDraftByUUID(@RequestParam(required = false) String uuid, @RequestBody DraftDTO body) {
    try {
      return this.draftsService.save(uuid, body);
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  @DeleteMapping("/delete/{uuid}")
  public Response deleteDraftByUUID(@PathVariable String uuid) {
    try {
      return this.draftsService.delete(uuid);
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }
}