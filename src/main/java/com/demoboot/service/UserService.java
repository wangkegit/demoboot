package com.demoboot.service;

import com.demoboot.entity.User;

import java.util.List;

public interface UserService {
    User getUser(Integer id);

    List<User> getAllUser();


}
