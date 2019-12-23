package com.demoboot.service.Impl;

import com.demoboot.entity.User;
import com.demoboot.repository.UserRepository;
import com.demoboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Override
    public User getUser(Integer id) {
      //return  repository.findById(id);
      return repository.getUser(id);
    }

    @Override
    public List<User> getAllUser() {
        return repository.findAll();
    }
}
