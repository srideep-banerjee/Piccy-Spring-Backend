package me.projects.piccy.auth;

import me.projects.piccy.common.id_to_name.UserIdToName;
import me.projects.piccy.common.id_to_name.UserIdToNameRepository;
import me.projects.piccy.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserIdToNameRepository userIdToNameRepository;

    @Autowired
    private ProfileService profileService;

    public void register(String userName, String password) {

        UserIdToName userIdToName = new UserIdToName(userName);
        userIdToName = userIdToNameRepository.save(userIdToName);

        UserEntity user = new UserEntity(userIdToName, passwordEncoder.encode(password));

        userRepository.save(user);

        profileService.createProfile(userIdToName,null);
    }


    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
