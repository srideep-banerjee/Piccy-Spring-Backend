package me.projects.piccy.posts.likes;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "post_likes")
@IdClass(PostLike.class)
public class PostLike implements Serializable {

    @Id
    @Column(name = "post_id", nullable = false, updatable = false,
            columnDefinition = "integer references posts(post_id) on delete cascade")
    private Long postId;

    @Id
    @Column(name = "liker_id", nullable = false, updatable = false,
            columnDefinition = "integer references users(user_id) on delete cascade")
    private Long likerId;

    public PostLike(Long postId, Long likerId) {
        this.postId = postId;
        this.likerId = likerId;
    }

    PostLike() {}

    public Long getPostId() {
        return postId;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostLike postLike)) return false;

        return Objects.equals(postId, postLike.postId) && Objects.equals(likerId, postLike.likerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, likerId);
    }
}
