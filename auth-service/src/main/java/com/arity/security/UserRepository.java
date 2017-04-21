package com.arity.security;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by sherjeelg on 4/20/2017.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByLogin(String login);

    User findByName(String username);
}