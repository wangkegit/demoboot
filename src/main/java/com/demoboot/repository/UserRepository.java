package com.demoboot.repository;

import com.demoboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("from User where id =:id")
    public User getUser(@Param("id") Integer id);

    public List<User> findAll();

    public User saveAndFlush(User user) ;

}
