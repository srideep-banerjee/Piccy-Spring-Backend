package me.projects.piccy.posts.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long postId) {
        super("No post found with id: " + postId);
    }
}
