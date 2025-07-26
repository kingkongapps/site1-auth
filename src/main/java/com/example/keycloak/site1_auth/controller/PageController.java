package com.example.keycloak.site1_auth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class PageController {

    @Value("${site.url1}")
    private String siteUrl1;
    @Value("${site.url2}")
    private String siteUrl2;
    @Value("${site.url3}")
    private String siteUrl3;

    @GetMapping(value = "/")
    public String goHome(Model model) {
        System.out.println("goHome()...");

        model.addAttribute("SITE_URL1", siteUrl1);
        model.addAttribute("SITE_URL2", siteUrl2);
        model.addAttribute("SITE_URL3", siteUrl3);

        return "/home";
    }

    @GetMapping(value = "/admin/admin-test1")
    public String goAdminTest1(Model model) {
        System.out.println("goAdminTest1()...");

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
//        Principal principal = authentication.getPrincipal();

        model.addAttribute("SITE_URL1", siteUrl1);
        model.addAttribute("SITE_URL2", siteUrl2);
        model.addAttribute("SITE_URL3", siteUrl3);

        return "/admin/admin-test1";
    }

    @GetMapping(value = "/admin/admin-test2")
    public String goAdminTest2(Model model) {
        System.out.println("goAdminTest2()...");

        model.addAttribute("SITE_URL1", siteUrl1);
        model.addAttribute("SITE_URL2", siteUrl2);
        model.addAttribute("SITE_URL3", siteUrl3);

        return "/admin/admin-test2";
    }

    @GetMapping(value = "/user/user-test1")
    public String goUserTest1(Model model) {
        System.out.println("goUserTest1()...");

        model.addAttribute("SITE_URL1", siteUrl1);
        model.addAttribute("SITE_URL2", siteUrl2);
        model.addAttribute("SITE_URL3", siteUrl3);

        return "/user/user-test1";
    }

    @GetMapping(value = "/user/user-test2")
    public String goUserTest2(Model model) {
        System.out.println("goUserTest2()...");

        model.addAttribute("SITE_URL1", siteUrl1);
        model.addAttribute("SITE_URL2", siteUrl2);
        model.addAttribute("SITE_URL3", siteUrl3);

        return "/user/user-test2";
    }

    @GetMapping(value = "/login")
    public String goLogin(Model model) {
        System.out.println("goLogin()...");

        model.addAttribute("SITE_URL1", siteUrl1);
        model.addAttribute("SITE_URL2", siteUrl2);
        model.addAttribute("SITE_URL3", siteUrl3);

        return "/login/login";
    }

    @GetMapping(value = "/token")
    public String goToken(Model model) {
        System.out.println("goToken()...");

        model.addAttribute("SITE_URL1", siteUrl1);
        model.addAttribute("SITE_URL2", siteUrl2);
        model.addAttribute("SITE_URL3", siteUrl3);

        return "/login/token";
    }

    @GetMapping(value = "/login/profile")
    public String goProfile(Model model) {
        System.out.println("goProfile()...");

        model.addAttribute("SITE_URL1", siteUrl1);
        model.addAttribute("SITE_URL2", siteUrl2);
        model.addAttribute("SITE_URL3", siteUrl3);

        return "/login/token";
    }

    @GetMapping(value = "/jwt-decode")
    public String goJWTDecode(Model model) {
        System.out.println("goJWTDecode()...");

        model.addAttribute("SITE_URL1", siteUrl1);
        model.addAttribute("SITE_URL2", siteUrl2);
        model.addAttribute("SITE_URL3", siteUrl3);

        return "/login/jwt-decode";
    }
}
