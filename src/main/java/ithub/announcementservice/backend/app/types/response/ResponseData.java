package ithub.announcementservice.backend.app.types.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"status", "message", "data"})
public class ResponseData<T> extends Response {
    private final T data;

    public ResponseData(int status, String message, T data) {
        super(status, message);
        this.data = data;
    }
}