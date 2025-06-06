package me.projects.piccy.common.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping
    private String serveIndex() {
        return "index.html";
    }
}
