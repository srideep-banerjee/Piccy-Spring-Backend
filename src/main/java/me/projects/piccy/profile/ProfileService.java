package me.projects.piccy.profile;

import me.projects.piccy.common.id_to_name.UserIdToName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public void createProfile(UserIdToName idToName, String pfp) {
        profileRepository.save(new Profile(idToName, pfp));
    }
}
