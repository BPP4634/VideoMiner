package aiss.grupo6.videoMiner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Video not found")
public class VideoNotFoundException extends Exception {

    Map<String, String> body = new HashMap<>();
    public VideoNotFoundException(String message) {
        super(message);
        body.put("statusCode", "404");
        body.put("status", HttpStatus.NOT_FOUND.toString());
        body.put("message", message);
    }

    public Map<String, String> getBody() {
        return this.body;
    }

}