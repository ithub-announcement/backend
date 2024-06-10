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
  public Response approveReview(@PathVariable UUID uuid) {
    return this.reviewService.approveReview(uuid);
  }

  @Operation(summary = "Отклонить заявку")
  @PostMapping("/reject/{uuid}")
  public Response rejectReview(@PathVariable UUID uuid, @RequestBody String comments) {
    return this.reviewService.rejectReview(uuid, comments);
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
  public Response getReviewByAuthor(@RequestBody String token){
    return this.reviewService.getReviewByAuthor(token);
  }

  @Operation(summary = "Получить количество заявок на рассмотрении у пользоваетеля")
  @GetMapping("/author/count")
  public Response getCountByAuthor(@RequestBody String token){
    return this.reviewService.getCountOfReviewByAuthor(token);
  }
}
