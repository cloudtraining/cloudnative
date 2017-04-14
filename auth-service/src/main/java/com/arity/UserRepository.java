package com.arity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;
import java.util.Set;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByLogin(String login);
}