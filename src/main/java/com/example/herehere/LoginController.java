package com.example.herehere;

import com.example.herehere.auth.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @GetMapping("/loginSuccess")
    public String getLoginSuccess(Principal principal) {

        customOAuth2UserService.testRefresh(principal);

        return "loginSuccess";
    }

    @GetMapping("/loginFailure")
    public String getLoginFailure() {
        return "loginFailure";
    }
}
