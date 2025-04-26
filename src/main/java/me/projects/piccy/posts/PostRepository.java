package me.projects.piccy.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.likes = p.likes + 1 WHERE p.id = :postId")
    void incrementLikes(Long postId);

    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.likes = p.likes - 1 WHERE p.id = :postId")
    void decrementLikes(Long postId);
}
