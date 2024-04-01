package ithub.announcementservice.backend.routes.announcements;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ithub.announcementservice.backend.app.types.response.Response;
import org.springframework.http.HttpStatus;
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
  public Response getAllAnnouncements() {
    try {
      return _service.getAllAnnouncements();
    } catch (Exception e) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error: " + e.getMessage());
    }
  }

  @GetMapping("{uuid}")
  @Operation(summary = "Получить обьявление по uuid")
  public Response deleteAnnouncementByUUID(@PathVariable UUID uuid) {
    try {
      return _service.getAnnouncementByUUID(uuid);
    } catch (Exception e) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error: " + e.getMessage());
    }
  }

  @DeleteMapping("{uuid}")
  @Operation(summary = "архирировать обьявление")
  public Response setAnnouncementArchive(@PathVariable UUID uuid) {
    try {
      return _service.setAnnouncementArchive(uuid);
    } catch (Exception e) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error: " + e.getMessage());
    }
  }

}
