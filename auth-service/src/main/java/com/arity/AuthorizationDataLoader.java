package com.arity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by sherjeelg on 4/14/2017.
 */
public class AuthorizationDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Role roleAdmin = new Role();
        roleAdmin.setId(1);
        roleAdmin.setName("ROLE_ADMIN");

        Role roleUser = new Role();
        roleUser.setId(2);
        roleUser.setName("ROLE_USER");

        User u1 = new User();
        u1.setId(1);
        u1.setLogin("sherjeel.ghouse@gmail.com");
        u1.setName("Sherjeel Ghouse");
        u1.getRoles().add(roleAdmin);
        u1.getRoles().add(roleUser);
        userRepository.save(u1);

        User u2 = new User();
        u1.setId(2);
        u1.setLogin("scott.kramer@visualsuccess.org");
        u1.setName("Scott Kramer");
        u1.getRoles().add(roleAdmin);
        u1.getRoles().add(roleUser);
        userRepository.save(u2);
    }
}