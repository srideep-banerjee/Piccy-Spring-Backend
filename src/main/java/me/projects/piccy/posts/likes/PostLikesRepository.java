package me.projects.piccy.posts.likes;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PostLikesRepository extends CrudRepository<PostLike, PostLike> {

    Set<PostLike> findByLikerId(Long likerId);
}
