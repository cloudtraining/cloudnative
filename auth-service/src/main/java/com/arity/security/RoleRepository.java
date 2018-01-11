package com.arity.security;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by sherjeelg on 4/20/2017.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String role);
}