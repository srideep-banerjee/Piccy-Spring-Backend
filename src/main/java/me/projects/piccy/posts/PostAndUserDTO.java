package me.projects.piccy.posts;


import me.projects.piccy.profile.UserProfileDTO;

import java.time.Instant;

public record PostAndUserDTO(Long postId, String title, String url, Instant createdAt, UserProfileDTO creator) {
}
