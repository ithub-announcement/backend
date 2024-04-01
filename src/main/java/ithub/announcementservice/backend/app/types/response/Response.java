package ithub.announcementservice.backend.app.types.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
