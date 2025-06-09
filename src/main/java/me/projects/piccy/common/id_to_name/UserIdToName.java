package me.projects.piccy.common.id_to_name;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "user_id_to_name")
public class UserIdToName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NaturalId(mutable = true)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    UserIdToName() {}

    public UserIdToName(String username) {
        this.username = username;
    }

    public UserIdToName(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
