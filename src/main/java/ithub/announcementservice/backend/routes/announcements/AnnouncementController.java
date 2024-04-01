package ithub.announcementservice.backend.routes.announcements;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ithub.announcementservice.backend.app.types.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "обьявления")
@RestController
@RequestMapping("/announcements")
public class AnnouncementController {
  @Autowired
  private final AnnouncementService announcementService;

  public AnnouncementController(final AnnouncementService announcementService) {
    this.announcementService = announcementService;
  }

  @GetMapping()
  @Operation(summary = "Получить все обьявления")
  public Response getAllAnnouncements() {
    try {
      return this.announcementService.getAllAnnouncements();
    } catch (Exception e) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error: " + e.getMessage());
    }
  }

  @GetMapping("{uuid}")
  @Operation(summary = "Получить обьявление по uuid")
  public Response getAnnouncementsByUUID(@PathVariable UUID uuid) {
    try {
      return this.announcementService.getAnnouncementByUUID(uuid);
    } catch (Exception e) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error: " + e.getMessage());
    }
  }

  @DeleteMapping("{uuid}")
  @Operation(summary = "архирировать обьявление")
  public Response deleteAnnouncementByUUID(@PathVariable UUID uuid) {
    try {
      return this.announcementService.setAnnouncementArchive(uuid);
    } catch (Exception e) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error: " + e.getMessage());
    }
  }
}
