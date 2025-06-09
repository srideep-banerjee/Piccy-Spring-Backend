package me.projects.piccy.common.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(value = "/{path1:.*}/{path2:^[^.]*$}")
    private String serveIndex(HttpServletRequest request) {
        return "/index.html";
    }
}
