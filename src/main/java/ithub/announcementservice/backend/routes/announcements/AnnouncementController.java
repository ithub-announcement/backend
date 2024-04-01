package ithub.announcementservice.backend.routes.announcements;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ithub.announcementservice.backend.app.types.response.Response;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "обьявления")
@RestController
@RequestMapping("/announcements")
public class AnnouncementController {
  private final AnnouncementService _service;

  public AnnouncementController(AnnouncementService service) {
    _service = service;
  }

  @GetMapping()
  @Operation(summary = "Получить все обьявления")
  public Response getAllAnnouncements(int page,int size) {
    return _service.getAllAnnouncements(page, size);
  }
  @GetMapping("{uuid}")
  @Operation(summary = "Получить обьявление по uuid")
  public Response getAnnouncementsByUUID(@PathVariable UUID uuid) {
    return _service.getAnnouncementByUUID(uuid);
  }
  @DeleteMapping("{uuid}")
  @Operation(summary = "архирировать обьявление")
  public Response setAnnouncementArchive(@PathVariable UUID uuid) {
    return _service.setAnnouncementArchive(uuid);
  }
}
