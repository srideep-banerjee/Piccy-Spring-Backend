package me.projects.piccy.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

@Controller
@RequestMapping(path = "/api")
public class AuthController {

    private final AuthUserService authUserService;

    AuthController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestPart String username, @RequestPart String password) {
        authUserService.register(
                username,
                password
        );
        return ResponseEntity.ok("Request accepted");
    }
}
