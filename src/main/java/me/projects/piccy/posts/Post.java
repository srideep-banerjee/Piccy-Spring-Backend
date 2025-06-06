package me.projects.piccy.posts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import me.projects.piccy.profile.Profile;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "posts")
@NamedEntityGraph(
        name = "Post.creator",
        attributeNodes = @NamedAttributeNode(
                value = "creatorProfile",
                subgraph = "creatorProfile.idToName"
        ),
        subgraphs = @NamedSubgraph(
                name = "creatorProfile.idToName",
                attributeNodes = @NamedAttributeNode("idToName")
        )
)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @JsonIgnore
    @ManyToOne(targetEntity = Profile.class, cascade = CascadeType.REMOVE)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "creator", insertable = false, updatable = false)
    private Profile creatorProfile;

    @Column(name = "creator", nullable = false, updatable = false,
            columnDefinition = "integer references profiles(user_id) on delete cascade")
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

    public PostAndUserDTO toDto(Boolean likedByUser) {
        return new PostAndUserDTO(
                id,
                title,
                url,
                createdAt.toInstant(),
                likes,
                likedByUser,
                creatorProfile.toDto()
        );
    }
}
