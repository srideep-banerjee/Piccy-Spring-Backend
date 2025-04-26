package me.projects.piccy.posts;

import me.projects.piccy.auth.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Controller
@RequestMapping(path = "/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestPart String title, @RequestPart MultipartFile postFile, @AuthenticationPrincipal UserEntity userEntity) {

        try {
            Post result = postService.createPost(title, userEntity, postFile);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> fetchPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.retrievePost(postId));
    }

    @PutMapping("/toggleLike/{postId}")
    public ResponseEntity<Boolean> toggleLike(@PathVariable Long postId, @AuthenticationPrincipal UserEntity userEntity) {
        return ResponseEntity.ok(postService.togglePostLike(postId, userEntity));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId , @AuthenticationPrincipal UserEntity userEntity) {
        postService.deletePost(postId, userEntity);
        return ResponseEntity.ok("Request Accepted");
    }
}
