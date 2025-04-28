package me.projects.piccy.posts;

import me.projects.piccy.auth.UserEntity;
import me.projects.piccy.media.MediaService;
import me.projects.piccy.posts.likes.PostLike;
import me.projects.piccy.posts.likes.PostLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private MediaService mediaService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikesRepository postLikesRepository;

    Post createPost(String title, UserEntity user, MultipartFile inputFile) throws IOException {
        UUID uuid = mediaService.saveFile(inputFile);
        String url = "/media/" + uuid;

        Post post = new Post(title, user.getUserId(), url);
        post = postRepository.save(post);
        return post;
    }

    Post retrievePost(Long postId) {
        return postRepository
                .findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    boolean togglePostLike(Long postId, UserEntity user) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException(postId);
        }

        PostLike postLike = new PostLike(postId, user.getUserId());
        boolean liked = postLikesRepository.existsById(postLike);

        if (liked) {
            postLikesRepository.delete(postLike);
            postRepository.decrementLikes(postId);
        } else {
            postLikesRepository.save(postLike);
            postRepository.incrementLikes(postId);
        }
        return !liked;
    }

    void deletePost(Long postId, UserEntity user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        if (!Objects.equals(post.getCreator(), user.getUserId())) {
            throw new PostCreatorMismatchException();
        }

        postRepository.deleteById(postId);

        UUID uuid = UUID.fromString(post.getUrl().substring(7));
        mediaService.deleteFile(uuid);
    }

    List<Post> getPosts(Sort sort) {
        return postRepository.findAll(sort);
    }
}
