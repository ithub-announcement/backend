package ithub.announcementservice.backend.routes.announcements.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ithub.announcementservice.backend.core.models.response.types.Response;
import ithub.announcementservice.backend.routes.announcements.payloadUUid;
import ithub.announcementservice.backend.routes.announcements.services.AnnouncementService;
import ithub.announcementservice.backend.routes.tags.models.TagEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@Tag(name = "Обьявления")
@RestController
@RequestMapping("/announcements")
public class AnnouncementController {
  private final AnnouncementService announcementService;

  public AnnouncementController(final AnnouncementService announcementService) {
    this.announcementService = announcementService;
  }

  @PostMapping("/toPublic")
  @Operation(summary = "Отправить в публикацию")
  public Response sendToPublic(@RequestHeader String Authorization, @RequestBody payloadUUid uuid){
    String uuidString = uuid.getUuid();
    log.info("Received UUID: {}", uuidString);
    try {
      UUID uuidValue = UUID.fromString(uuidString);
      log.info("Parsed UUID: {}", uuidValue);
      return this.announcementService.sendToPublication(uuidValue, Authorization);
    }catch (Exception e){
      log.error("Error sending announcement: ", e);
      throw new IllegalStateException();
    }
  }

  @GetMapping("/tag")
  @Operation(summary = "Получить по тэгам")
  public Response findAnnouncementsByTags(@RequestBody List<TagEntity> tags){
    return this.announcementService.findByTags(tags);
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