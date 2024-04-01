package ithub.announcementservice.backend.routes.announcements;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ithub.announcementservice.backend.app.types.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Обьявления")
@RestController
@RequestMapping("/announcements")
public class AnnouncementController {
  private final AnnouncementService announcementService;

  public AnnouncementController(final AnnouncementService announcementService) {
    this.announcementService = announcementService;
  }

  @GetMapping()
  @Operation(summary = "Получить все обьявления")
  public Response findAllAnnouncements() {
    try {
      return this.announcementService.findAll();
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  @GetMapping("{uuid}")
  @Operation(summary = "Получить обьявление по uuid")
  public Response findAnnouncementByUUID(@PathVariable UUID uuid) {
    try {
      return this.announcementService.findByUUID(uuid);
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  @DeleteMapping("{uuid}")
  @Operation(summary = "Архирировать обьявление")
  public Response deleteAnnouncementByUUID(@PathVariable UUID uuid) {
    try {
      return this.announcementService.deleteByUUID(uuid);
    } catch (Exception err) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }
}
