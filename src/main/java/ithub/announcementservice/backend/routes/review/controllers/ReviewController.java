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
    reviewService = _reviewService;
  }

  @Operation(summary = "Принять на модерацию")
  @PostMapping("/send-to-review")
  public Response acceptReview(@RequestBody ReviewAcceptPayload payload) {
    return reviewService.acceptReview(payload);
  }

  @Operation(summary = "Одобрить заявку")
  @PostMapping("/approve/{uuid}")
  public Response approveReview(@PathVariable UUID uuid) {
    return reviewService.approveReview(uuid);
  }

  @Operation(summary = "Отклонить заявку")
  @PostMapping("/reject/{uuid}")
  public Response rejectReview(@PathVariable UUID uuid, @RequestBody String comments) {
    return reviewService.rejectReview(uuid, comments);
  }

  @Operation(summary = "Получить одну заявку по UUID")
  @GetMapping("/{uuid}")
  public Response getReview(@PathVariable UUID uuid) {
    return reviewService.getReview(uuid);
  }

  @Operation(summary = "Получить все заявки")
  @GetMapping()
  public Response getReviews() {
    return reviewService.getReviews();
  }

  @Operation(summary = "Удалить заявку")
  @DeleteMapping("/delete/{uuid}")
  public Response deleteReview(@PathVariable UUID uuid) {
    return reviewService.deleteReview(uuid);
  }

  @Operation(summary = "Получить количество заявок на рассмотрении")
  @GetMapping("/count")
  public Response getCount(){
    return reviewService.getCountOfReview();
  }

  @Operation(summary = "Получить заявки на рассмотрении определенного пользователя")
  @GetMapping("/reviewByAuthor")
  public Response getReviewByAuthor(@RequestBody String token){
    return reviewService.getReviewByAuthor(token);
  }
}
