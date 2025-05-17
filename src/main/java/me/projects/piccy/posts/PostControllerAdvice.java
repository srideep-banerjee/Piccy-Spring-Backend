package me.projects.piccy.posts;

import me.projects.piccy.media.MediaException;
import me.projects.piccy.posts.exception.PostCreatorMismatchException;
import me.projects.piccy.posts.exception.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
class PostControllerAdvice {

    @ExceptionHandler(value = {PostNotFoundException.class})
    ResponseEntity<Map<String, String>> handlePostNotFound(PostNotFoundException e) {
        return new ResponseEntity<>(getErrorPayload(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {PostCreatorMismatchException.class})
    ResponseEntity<Map<String, String>> handleCreatorMismatch() {
        return new ResponseEntity<>(
                getErrorPayload("Action exclusively available to creator"),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(value = {ResponseStatusException.class})
    ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException e) {
        return new ResponseEntity<>(getErrorPayload(e.getReason()), e.getStatusCode());
    }

    @ExceptionHandler(value = {MediaException.class})
    ResponseEntity<Map<String, String>> handleMediaException(MediaException e) {
        return new ResponseEntity<>(getErrorPayload(e.getMessage()), HttpStatus.PAYLOAD_TOO_LARGE);
    }

    Map<String, String> getErrorPayload(String body) {
        Map<String, String> response = new HashMap<>();
        response.put("cause", body);
        return response;
    }
}
