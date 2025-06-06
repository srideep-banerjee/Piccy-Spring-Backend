package me.projects.piccy.auth;

import org.springframework.http.ResponseEntity;
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

    @GetMapping("/signup")
    public String showRegistrationForm() {
        return "index.html";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody MultiValueMap<String, String> userData) {
        authUserService.register(
                userData.get("username").getFirst(),
                userData.get("password").getFirst()
        );
        return ResponseEntity.ok("Request accepted");
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "index.html";
    }
}
