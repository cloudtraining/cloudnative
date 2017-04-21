package com.arity.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sherjeelg on 4/21/2017.
 */
@RestController
public class UserPasswordAuthService {

    @RequestMapping("/user")
    @ResponseBody
    public Map<String, Object> user(Principal principal) {

        Map<String, Object> map = new LinkedHashMap<String, Object>();

        map.put("name", principal.getName());

        map.put("roles", AuthorityUtils.authorityListToSet(((Authentication) principal)
                .getAuthorities()));

        return map;
    }

}
