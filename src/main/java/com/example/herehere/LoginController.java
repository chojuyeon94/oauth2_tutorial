package com.example.herehere;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/loginSuccess")
    public String getLoginSuccess() {
        return "loginSuccess";
    }

    @GetMapping("/loginFailure")
    public String getLoginFailure() {
        return "loginFailure";
    }
}
