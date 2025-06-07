package me.projects.piccy.posts;

import me.projects.piccy.auth.UserEntity;
import me.projects.piccy.media.MediaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestPart String title, @RequestPart MultipartFile postFile, @AuthenticationPrincipal UserEntity userEntity) throws MediaException {

        try {
            Post result = postService.createPost(title, userEntity, postFile);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostAndUserDTO> fetchPost(@PathVariable Long postId, @AuthenticationPrincipal UserEntity userEntity) {
        return ResponseEntity.ok(postService.retrievePost(postId, userEntity));
    }

    @GetMapping("/list")
    ResponseEntity<List<PostAndUserDTO>> listPosts(@RequestParam Map<String, String> sortOrders, @AuthenticationPrincipal UserEntity userEntity) {
        List<Sort.Order> orders = new ArrayList<>();

        for (var entry: sortOrders.entrySet()) {
            PostSortOrder postSortOrder;

            try {
                postSortOrder = PostSortOrder.valueOf(entry.getValue());
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "query value must be ASC or DESC");
            }

            switch (postSortOrder) {
                case ASC -> orders.add(Sort.Order.asc(entry.getKey()));
                case DESC -> orders.add(Sort.Order.desc(entry.getKey()));
            }
        }

        try {
            return ResponseEntity.ok(postService.getPosts(Sort.by(orders), userEntity));
        } catch (PropertyReferenceException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unknown property: " + e.getPropertyName()
            );
        }
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

enum PostSortOrder {
    ASC, DESC
}
