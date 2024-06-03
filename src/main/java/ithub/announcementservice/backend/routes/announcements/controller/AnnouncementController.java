package ithub.announcementservice.backend.routes.announcements.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ithub.announcementservice.backend.app.types.response.Response;
import ithub.announcementservice.backend.routes.announcements.service.AnnouncementService;
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
      return this.announcementService.findAll();

  }

  @GetMapping("{uuid}")
  @Operation(summary = "Получить обьявление по uuid")
  public Response findAnnouncementByUUID(@PathVariable UUID uuid) {
      return this.announcementService.findByUUID(uuid);
  }

  @DeleteMapping("{uuid}")
  @Operation(summary = "Архирировать обьявление")
  public Response deleteAnnouncementByUUID(@PathVariable UUID uuid) {
      return this.announcementService.deleteByUUID(uuid);

  }
}
