package edu.auburn.cpsc4970.webapp5a;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {

    @GetMapping("/login")
    public String index(Model model) {
        return "login";
    }

    @GetMapping("/login_handler")
    public String loginHandler(Model model) {
        return "index";
    }

    @GetMapping("/internal")
    public String internalHandler(Model model) {
        return "internal";
    }

    @GetMapping("/pii")
    public String ssnHandler(Model model) {
        return "pii";
    }

}
