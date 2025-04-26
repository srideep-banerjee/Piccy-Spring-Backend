package me.projects.piccy.posts;

class PostNotFoundException extends RuntimeException {
    PostNotFoundException(Long postId) {
        super("No post found with id: " + postId);
    }
}
