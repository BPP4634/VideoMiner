package aiss.grupo6.videoMiner.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error when fetching data")
public class InternalErrorException extends Exception {

    Map<String, String> body = new HashMap<>();
    public InternalErrorException(String message) {
        super(message);
        body.put("statusCode", "500");
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        body.put("message", message);
    }

    public Map<String, String> getBody() {
        return this.body;
    }

}
