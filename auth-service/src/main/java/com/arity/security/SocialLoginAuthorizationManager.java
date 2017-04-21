package com.arity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sherjeelg on 4/20/2017.
 */
@Component
public class SocialLoginAuthorizationManager {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public void addSocialUser(Principal principal) {

        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;

        Authentication authentication = oAuth2Authentication.getUserAuthentication();

        Map<String, String> authenticationDetails = (LinkedHashMap<String, String>) authentication.getDetails();

        User user = new User();

        user.setName(authenticationDetails.get("name"));

        user.setLogin(authenticationDetails.get("id"));

        user.setEnabled(true);

        Role role = roleRepository.findByName("ROLE_ADMIN");
        user.getRoles().add(role);

        userRepository.save(user);
    }
}
