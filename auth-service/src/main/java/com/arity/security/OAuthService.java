package com.arity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@EnableOAuth2Client
@RestController
public class OAuthService {

    @Autowired
    AuthUserDetailsService userDetailsService;

    public static void main(String[] args) {

        SpringApplication.run(OAuthService.class, args);
    }

    @RequestMapping(value = "/user")
    public Principal user(Principal principal) {

        return principal;
    }
}
