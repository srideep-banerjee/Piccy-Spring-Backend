package me.projects.piccy.profile;

import me.projects.piccy.auth.UserEntity;
import me.projects.piccy.media.MediaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping(path = "/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/updatePfp")
    public ResponseEntity<String> updatePfp(@AuthenticationPrincipal UserEntity userEntity, @RequestPart MultipartFile newPfp) throws MediaException, IOException {
        profileService.updatePfp(userEntity, newPfp);
        return ResponseEntity.ok("Request accepted");
    }

    @PostMapping("/updateUsername")
    public ResponseEntity<String> updateUsername(@AuthenticationPrincipal UserEntity userEntity, @RequestBody String username) {
        profileService.updateUsername(userEntity, username);
        return ResponseEntity.ok("Request accepted");
    }

    @GetMapping
    public  ResponseEntity<UserProfileDTO> getProfile(@AuthenticationPrincipal UserEntity userEntity) {
        return ResponseEntity.ok(profileService.fetchUserProfile(userEntity));
    }
}
