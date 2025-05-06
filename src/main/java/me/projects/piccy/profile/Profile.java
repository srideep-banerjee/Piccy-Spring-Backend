package me.projects.piccy.profile;

import jakarta.persistence.*;
import me.projects.piccy.common.id_to_name.UserIdToName;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @Access(AccessType.PROPERTY)
    @JoinColumn(name = "user_id", nullable = false, unique = true, columnDefinition = "integer references user_id_to_name(user_id) on delete cascade on update cascade")
    private Long userId;

    @OneToOne(targetEntity = UserIdToName.class, cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserIdToName idToName;

    @Column(name = "pfp")
    private String pfp;

    Profile() {
    }

    Profile(UserIdToName idToName, String pfp) {
        this.idToName = idToName;
        this.userId = idToName.getUserId();
        this.pfp = pfp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserProfileDTO toDto() {
        return new UserProfileDTO(idToName.getUserId(), idToName.getUsername(), pfp);
    }
}
