package org.spring.springsecuritydemo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResourcesController {

    @GetMapping("/admin/resources")
    public String getResources() {
        return "admin/resources";
    }
}
