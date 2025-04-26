package me.projects.piccy.posts;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "creator", nullable = false, updatable = false)
    private Long creator;

    @Column(name = "url", nullable = false,  updatable = false)
    private String url;

    private Long likes = 0L;

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
}
