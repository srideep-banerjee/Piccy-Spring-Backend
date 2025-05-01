package me.projects.piccy.posts;

import me.projects.piccy.auth.UserDTO;

import java.time.Instant;

public record PostAndUserDTO(Long postId, String title, String url, Instant createdAt, UserDTO creator) {
}
