package me.projects.piccy.posts;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.likes = p.likes + 1 WHERE p.id = :postId")
    void incrementLikes(Long postId);

    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.likes = p.likes - 1 WHERE p.id = :postId")
    void decrementLikes(Long postId);

    @Override
    @Nonnull
    @EntityGraph(value = "Post.creator")
    List<Post> findAll(@Nonnull Sort sort);

    @EntityGraph(value = "Post.creator")
    List<Post> findByCreatorProfile_UserId(Long userId, Sort sort);
}
