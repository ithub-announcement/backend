package ithub.announcementservice.backend.core.models.response.types;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"status", "message"})
public class Response {
  private final int status;
  private final String message;

  public Response(int status, String message) {
    this.status = status;
    this.message = message;
  }
}
