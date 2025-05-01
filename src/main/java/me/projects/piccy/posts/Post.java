package me.projects.piccy.posts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import me.projects.piccy.auth.UserEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @JsonIgnore
    @ManyToOne(targetEntity = UserEntity.class, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "creator", insertable = false, updatable = false)
    private UserEntity creatorUser;

    @Column(name = "creator", nullable = false, updatable = false,
            columnDefinition = "integer references users(user_id) on delete cascade")
    private Long creator;

    @Column(name = "url", nullable = false,  updatable = false)
    private String url;

    @ColumnDefault("0")
    private Long likes = 0L;

    @CreationTimestamp
    @ColumnDefault("current_timestamp")
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt = Timestamp.from(Instant.now());

    public Post(String title, Long creator, String url) {
        this.title = title;
        this.creator = creator;
        this.url = url;
    }

    public Post() {}

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getCreator() {
        return creator;
    }

    public String getUrl() {
        return url;
    }

    public Long getLikes() {
        return likes;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public PostAndUserDTO toDto() {
        return new PostAndUserDTO(id, title, url, createdAt.toInstant(), creatorUser.toDto());
    }
}
