package ithub.announcementservice.backend.routes.review;

import io.swagger.v3.oas.annotations.servers.Server;
import ithub.announcementservice.backend.app.config.Mapper;
import ithub.announcementservice.backend.app.domain.models.AnnouncementStatus;
import ithub.announcementservice.backend.app.domain.models.entities.Announcement;
import ithub.announcementservice.backend.app.domain.repositories.AnnouncementRepository;
import ithub.announcementservice.backend.app.types.response.Response;
import ithub.announcementservice.backend.app.types.response.ResponseData;
import ithub.announcementservice.backend.routes.review.models.ReviewRepository;
import ithub.announcementservice.backend.routes.review.models.StatusReview;
import ithub.announcementservice.backend.routes.review.models.Review;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Server
public class ReviewService {
  private final AnnouncementRepository announcementRepository;
  private final ReviewRepository reviewRepository;
  private final Mapper mapper;

  public ReviewService(final AnnouncementRepository repository, ReviewRepository reviewRepository, final Mapper mapper) {
    this.announcementRepository = repository;
      this.reviewRepository = reviewRepository;
      this.mapper = mapper;
  }

  public Response acceptReview(UUID uuid, List<Integer> tags){
    try{
      if (uuid == null){
        return new Response(HttpStatus.NO_CONTENT.value(), "UUID is empty");
      }

      Optional<Announcement> current= announcementRepository.findById(uuid);

      if (current.isEmpty()){
        return new Response(HttpStatus.NO_CONTENT.value(), "Нет такого объявления");
      }

      Announcement announcement = current.get();

      if (announcement.getStatus() != AnnouncementStatus.DRAFT){
        return new Response(HttpStatus.NO_CONTENT.value(), "Это не черновик");
      }

      Review rev = new Review();
      rev.setStatusReview(StatusReview.review);

      rev.setUuid(announcement.getUuid());
      rev.setDateTime(announcement.getDateTime());

      rev.setTitle(announcement.getTitle());
      rev.setContent(announcement.getContent());

      rev.setAuthorId(announcement.getAuthorId());
      rev.setTags(tags);

      reviewRepository.save(rev);
      return new Response(HttpStatus.OK.value(), "Успешно принят");
    }catch (Exception err){
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  public Response rejectReview(UUID uuid, String comments){
    try{
      if (uuid == null){
        return new Response(HttpStatus.NO_CONTENT.value(), "UUID is empty");
      }

      Optional<Review> current= reviewRepository.findById(uuid);

      if (current.isEmpty()){
        return new Response(HttpStatus.NO_CONTENT.value(), "Нет такого объявления");
      }

      Review review = current.get();

      review.setStatusReview(StatusReview.reject);
      review.setComments(comments);

      reviewRepository.save(review);
      return new Response(HttpStatus.OK.value(), "Успешно отклонена");
    }catch (Exception err){
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  public Response approveReview(UUID uuid){
    try {
      if (uuid == null){
        return new Response(HttpStatus.NO_CONTENT.value(), "UUID is empty");
      }

      Optional<Review> current= reviewRepository.findById(uuid);

      if (current.isEmpty()){
        return new Response(HttpStatus.NO_CONTENT.value(), "Нет такого объявления");
      }

      Review review = current.get();
      review.setStatusReview(StatusReview.accept);

      reviewRepository.save(review);
      return new Response(HttpStatus.OK.value(), "Успешно одобрена");
    }catch (Exception err){
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  public Response getReview(UUID uuid){
    try {
      if (uuid == null){
        return new Response(HttpStatus.NO_CONTENT.value(), "UUID is empty");
      }

      Optional<Review> current= reviewRepository.findById(uuid);

      if (current.isEmpty()){
        return new Response(HttpStatus.NO_CONTENT.value(), "Нет такого объявления");
      }

      return new ResponseData<Review>(HttpStatus.OK.value(), "Успешно получено", current.get());
    }catch (Exception err){
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }

  public Response getReviews(){
    try {
      return new ResponseData<List<Review>>(HttpStatus.OK.value(),"Успешно получено",reviewRepository.findAll());
    }catch (Exception err){
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
  }
}
