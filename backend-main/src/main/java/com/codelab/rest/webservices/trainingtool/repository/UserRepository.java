package com.codelab.rest.webservices.trainingtool.repository;

import com.codelab.rest.webservices.trainingtool.model.User;

import java.util.List;
import java.util.Optional;
public interface UserRepository extends IdentifiableRepository<User> {
    Optional<User> findByEmail(String email);
}