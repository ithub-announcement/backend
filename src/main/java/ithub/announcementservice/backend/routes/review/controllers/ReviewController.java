package ithub.announcementservice.backend.routes.review.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import ithub.announcementservice.backend.core.models.response.types.Response;
import ithub.announcementservice.backend.routes.review.models.ReviewAcceptPayload;
import ithub.announcementservice.backend.routes.review.services.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Модерация", description = "Сервис модерации объявлений")
@RestController
@RequestMapping("/review")
public class ReviewController {
  private final ReviewService reviewService;

  public ReviewController(final ReviewService _reviewService) {
    this.reviewService = _reviewService;
  }

  @Operation(summary = "Принять на модерацию")
  @PostMapping("/send-to-review")
  public Response acceptReview(@RequestBody ReviewAcceptPayload payload) {
    return this.reviewService.acceptReview(payload);
  }

  @Operation(summary = "Одобрить заявку")
  @PostMapping("/approve/{uuid}")
  public Response approveReview(@RequestHeader String Authorization, @PathVariable UUID uuid) {
    return this.reviewService.approveReview(uuid, Authorization);
  }

  @Operation(summary = "Отклонить заявку")
  @PostMapping("/reject/{uuid}")
  public Response rejectReview(@RequestHeader String Authorization, @PathVariable UUID uuid, @RequestBody String comments) {
    return this.reviewService.rejectReview(uuid, comments, Authorization);
  }

  @Operation(summary = "Изменить")
  @PostMapping("/rename/{uuid}")
  public Response renameReview(@RequestHeader String Authorization, @PathVariable UUID uuid){
    return null;
  }

  @Operation(summary = "Получить одну заявку по UUID")
  @GetMapping("/{uuid}")
  public Response getReview(@PathVariable UUID uuid) {
    return this.reviewService.getReview(uuid);
  }

  @Operation(summary = "Получить все заявки")
  @GetMapping()
  public Response getReviews() {
    return this.reviewService.getReviews();
  }

  @Operation(summary = "Удалить заявку")
  @DeleteMapping("/delete/{uuid}")
  public Response deleteReview(@PathVariable UUID uuid) {
    return this.reviewService.deleteReview(uuid);
  }

  @Operation(summary = "Получить количество заявок на рассмотрении")
  @GetMapping("/count")
  public Response getCount(){
    return this.reviewService.getCountOfReview();
  }

  @Operation(summary = "Получить заявки на рассмотрении определенного пользователя")
  @GetMapping("/author/all")
  public Response getReviewByAuthor(@RequestHeader String Authorization){
    return this.reviewService.getReviewByAuthor(Authorization);
  }

  @Operation(summary = "Получить количество заявок на рассмотрении у пользоваетеля")
  @GetMapping("/author/count")
  public Response getCountByAuthor(@RequestHeader String Authorization){
    return this.reviewService.getCountOfReviewByAuthor(Authorization);
  }
}
