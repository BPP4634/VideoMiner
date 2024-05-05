package aiss.grupo6.videoMiner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Channel properties are not valid")
public class InvalidChannelException extends Exception {

    Map<String, String> body = new HashMap<>();
    public InvalidChannelException(String message) {
        super(message);
        body.put("statusCode", "400");
        body.put("status", HttpStatus.BAD_REQUEST.toString());
        body.put("message", message);
    }

    public Map<String, String> getBody() {
        return this.body;
    }

}
