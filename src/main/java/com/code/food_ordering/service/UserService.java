package com.code.food_ordering.service;

import com.code.food_ordering.entity.User;

public interface UserService {
    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;
}
