package ithub.announcementservice.backend.routes.review;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import ithub.announcementservice.backend.app.types.response.Response;

import ithub.announcementservice.backend.routes.tags.models.TagEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "ревью", description = "сервис модерации")
@RestController
@RequestMapping("/review")
public class ReviewController {
  private final ReviewService reviewService;

  public ReviewController(final ithub.announcementservice.backend.routes.review.ReviewService reviewservices) {
    reviewService = reviewservices;
  }

  @Operation(summary = "принять на модерацию")
  @PostMapping("/accept/{uuid}")
  public Response acceptReview(@PathVariable UUID uuid, @RequestHeader List<TagEntity> tags) {
    return reviewService.acceptReview(uuid, tags);
  }

  @Operation(summary = "одобрить")
  @PostMapping("/approve/{uuid}")
  public Response approveReview(@PathVariable UUID uuid) {
    return reviewService.approveReview(uuid);
  }

  @Operation(summary = "отклонить")
  @PostMapping("/reject/{uuid}")
  public Response rejectReview(@PathVariable UUID uuid, @RequestBody String comments) {
    return reviewService.rejectReview(uuid, comments);
  }

  @Operation(summary = "получить одно")
  @GetMapping("/{uuid}")
  public Response getReview(@PathVariable UUID uuid) {
    return reviewService.getReview(uuid);
  }

  @Operation(summary = "получить все")
  @GetMapping()
  public Response getReviews() {
    return reviewService.getReviews();
  }

  @Operation(summary = "удалить")
  @DeleteMapping("/delete/{uuid}")
  public Response deleteReview(@PathVariable UUID uuid) {
    return reviewService.deleteReview(uuid);
  }
}
