package me.projects.piccy.posts;

import me.projects.piccy.auth.UserEntity;
import me.projects.piccy.media.MediaException;
import me.projects.piccy.media.MediaService;
import me.projects.piccy.posts.exception.PostCreatorMismatchException;
import me.projects.piccy.posts.exception.PostNotFoundException;
import me.projects.piccy.posts.likes.PostLike;
import me.projects.piccy.posts.likes.PostLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class PostService {

    @Autowired
    private MediaService mediaService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikesRepository postLikesRepository;

    Post createPost(String title, UserEntity user, MultipartFile inputFile) throws IOException, MediaException {
        UUID uuid = mediaService.saveFile(inputFile);
        String url = MediaService.getUrlFromUUID(uuid);

        Post post = new Post(title, user.getUserId(), url);
        post = postRepository.save(post);
        return post;
    }

    PostAndUserDTO retrievePost(Long postId, UserEntity user) {
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        boolean likedByUser = user != null && postLikesRepository
                .existsById(new PostLike(postId, user.getUserId()));

        return post.toDto(likedByUser);
    }

    @Transactional(rollbackFor = {PostNotFoundException.class})
    protected boolean togglePostLike(Long postId, UserEntity user) {
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

        UUID uuid = MediaService.getUUIDFromUrl(post.getUrl());
        mediaService.deleteFile(uuid);
    }

    List<PostAndUserDTO> getPosts(Sort sort, UserEntity user) {
        Set<Long> userLikes;
        if (user != null) {
            List<Long> userLikesList = postLikesRepository
                    .findByLikerId(user.getUserId())
                    .stream()
                    .map(PostLike::getPostId)
                    .toList();
            userLikes = new HashSet<>(userLikesList);
        } else {
            userLikes = null;
        }

        return postRepository
                .findAll(sort)
                .stream()
                .map(post -> {
                    boolean liked = userLikes != null && userLikes.contains(post.getId());
                    return post.toDto(liked);
                })
                .toList();
    }

    List<PostAndUserDTO> getSelfPosts(Sort sort, UserEntity user) {
        Set<Long> userLikes = new HashSet<>(postLikesRepository
                .findByLikerId(user.getUserId())
                .stream()
                .map(PostLike::getPostId)
                .toList()
        );

        return postRepository
                .findByCreatorProfile_UserId(user.getUserId(), sort)
                .stream()
                .map(post -> post.toDto(userLikes.contains(post.getId())))
                .toList();
    }
}
