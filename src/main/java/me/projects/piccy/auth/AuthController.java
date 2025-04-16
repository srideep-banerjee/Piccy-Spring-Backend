package me.projects.piccy.auth;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {

    private final AuthUserService authUserService;

    AuthController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register.html";
    }

    @PostMapping("/register/save")
    public String registerUser(@RequestBody MultiValueMap<String, String> userData) {
        authUserService.register(
                userData.get("username").getFirst(),
                userData.get("password").getFirst()
        );
        return "redirect:/login";
    }
}
