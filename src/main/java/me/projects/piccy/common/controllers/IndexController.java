package me.projects.piccy.common.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(value = {"/{path:^[^.]*$}", "/*/{path:^[^.]*$}", "/*/*/{path:^[^.]*$}"})
    private String serveIndex() {
        return "/index.html";
    }
}
