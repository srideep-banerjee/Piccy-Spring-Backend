package me.projects.piccy.posts;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class PostControllerAdvice {

    @ExceptionHandler(value = {PostNotFoundException.class})
    ResponseEntity<String> handlePostNotFound(PostNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {PostCreatorMismatchException.class})
    ResponseEntity<String> handleCreatorMismatch() {
        return new ResponseEntity<>("Action exclusively available to creator", HttpStatus.FORBIDDEN);
    }
}
