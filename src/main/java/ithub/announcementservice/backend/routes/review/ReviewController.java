package ithub.announcementservice.backend.routes.review;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ithub.announcementservice.backend.app.types.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "ревбю" , description = "сервис модерации")
@RestController
@RequestMapping("/review")
public class ReviewController {
  private final ReviewService reviewService;

    public ReviewController(final ithub.announcementservice.backend.routes.review.ReviewService reviewservices) {
      reviewService = reviewservices;
    }


    @Operation(summary = "принять на модерацию")
  @PostMapping("/accept/{uuid}")
  public Response acceptReview(@PathVariable UUID uuid, @RequestBody List<Integer> tags){
    try {
      return reviewService.acceptReview(uuid, tags);
    }catch (Exception err){
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  @Operation(summary = "одобрить")
    @PostMapping("/approve/{uuid}")
    public Response approveReview(@PathVariable UUID uuid){
      try {
        return reviewService.approveReview(uuid);
      }catch (Exception err){
        return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
      }
    }

  @Operation(summary = "отклонить")
  @PostMapping("/reject/{uuid}")
  public Response rejectReview(@PathVariable UUID uuid, @RequestBody String comments){
    try {
      return reviewService.rejectReview(uuid, comments);
    }catch (Exception err){
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  @Operation(summary = "получить одно")
  @GetMapping("/{uuid}")
  public Response getReview(@PathVariable UUID uuid){
    try {
      return reviewService.getReview(uuid);
    }catch (Exception err){
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  @Operation(summary = "получить все")
  @GetMapping("/")
  public Response getReviews(){
    try {
      return reviewService.getReviews();
    }catch (Exception err){
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }
}
