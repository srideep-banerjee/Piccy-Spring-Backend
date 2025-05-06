package me.projects.piccy.auth;

import jakarta.persistence.*;
import me.projects.piccy.common.id_to_name.UserIdToName;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @Column(name = "user_id", columnDefinition = "integer references user_id_to_name(user_id) on delete cascade on update cascade")
    private Long userId;

    @OneToOne(targetEntity = UserIdToName.class, cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @PrimaryKeyJoinColumn(name = "user_id")
    private UserIdToName idToName;

    @Column(name = "password", nullable = false)
    private String password;

    public UserEntity() {
    }

    public UserEntity(UserIdToName idToName, String password) {
        this.idToName = idToName;
        this.userId = idToName.getUserId();
        this.password = password;
    }

    public Long getUserId() {
        return idToName.getUserId();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return idToName.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }
}