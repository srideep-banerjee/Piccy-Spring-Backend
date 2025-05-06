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

    @NaturalId
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    UserIdToName() {}

    public UserIdToName(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUserId(Long userId) {
        System.out.println("Set UserId = " + userId + " ---------------------------------------------");
        this.userId = userId;
    }

    public void setUsername(String username) {
        System.out.println("Set Username = " + username + " ---------------------------------------------");
        this.username = username;
    }
}
