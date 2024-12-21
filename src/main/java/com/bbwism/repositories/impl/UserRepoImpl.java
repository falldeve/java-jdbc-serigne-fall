package com.bbwism.repositories.impl;

import java.util.List;
import java.util.Optional;

import com.bbwism.Entites.User;
import com.bbwism.repositories.bd.core.Repository;

public interface UserRepoImpl extends Repository<User> {
    
    Optional<User> findById(int id);
    User findByTel(String tel);
    List<User> findAllActive();
    List<User> findByRole(String role);
    
}
