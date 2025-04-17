package me.projects.piccy.posts;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "post_likes")
@IdClass(PostLike.class)
public class PostLike implements Serializable {

    @Id
    @Column(name = "post_id")
    private Integer postId;

    @Id
    @Column(name = "liker_id")
    private Integer likerId;

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
