package me.projects.piccy.posts;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "post_id")
    private Integer id;

    @Column(name = "creator", nullable = false, updatable = false)
    private String creator;

    @Column(name = "url", updatable = false)
    private String url;

    private Integer likes;
}
