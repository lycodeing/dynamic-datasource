package com.yuanxiaotu.controller;

import com.yuanxiaotu.config.datasource.annotation.Master;
import com.yuanxiaotu.config.datasource.annotation.Slave;
import com.yuanxiaotu.entity.User;
import com.yuanxiaotu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author xiaotianyu
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserMapper userMapper;

    @Autowired
    public UserController(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Master
    @GetMapping("/master")
    public void master() {
        userMapper.insert(User.builder().name(Math.random() * 100 + "_master").build());
    }

    @Slave
    @GetMapping("/slave")
    public void slave() {
        userMapper.insert(User.builder().name(Math.random() * 100 + "_slave").build());
    }
}
