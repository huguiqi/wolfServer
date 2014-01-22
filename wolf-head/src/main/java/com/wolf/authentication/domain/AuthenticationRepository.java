package com.wolf.authentication.domain;

import com.wolf.authentication.persistent.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthenticationRepository {

    @Autowired
    private UserMapper userMapper;

    public Account getUserByUserName(String userName) {
        return userMapper.getUserByUserName(userName);
    }

    public List<Account> getUserList() {
        return userMapper.getUserList();
    }
}
