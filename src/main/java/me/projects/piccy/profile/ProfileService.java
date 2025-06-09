package me.projects.piccy.profile;

import me.projects.piccy.auth.UserEntity;
import me.projects.piccy.common.id_to_name.UserIdToName;
import me.projects.piccy.common.id_to_name.UserIdToNameRepository;
import me.projects.piccy.media.MediaException;
import me.projects.piccy.media.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserIdToNameRepository userIdToNameRepository;

    @Autowired
    private MediaService mediaService;

    public void createProfile(UserIdToName idToName, String pfp) {
        profileRepository.save(new Profile(idToName, pfp));
    }

    void updatePfp(UserEntity user, MultipartFile pfp) throws MediaException, IOException {
        Profile profile = profileRepository.findById(user.getUserId()).orElseThrow();

        String oldPfp = profile.toDto().pfp();

        String pfpUrl = MediaService.getUrlFromUUID(mediaService.saveFile(pfp));

        UserIdToName userIdToName = new UserIdToName(profile.getUserId(), user.getUsername());
        Profile updatedProfile = new Profile(userIdToName, pfpUrl);

        profileRepository.save(updatedProfile);

        if (oldPfp != null)
            mediaService.deleteFile(MediaService.getUUIDFromUrl(pfpUrl));
    }

    void updateUsername(UserEntity user, String username) {
        UserIdToName idToName = new UserIdToName(user.getUserId(), username);
        userIdToNameRepository.save(idToName);
    }

    UserProfileDTO fetchUserProfile(UserEntity user) {
        Profile profile = profileRepository
                .findById(user.getUserId())
                .orElseThrow();

        return profile.toDto();
    }
}
