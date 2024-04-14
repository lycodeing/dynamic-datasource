package com.yuanxiaotu.controller;

import com.yuanxiaotu.config.datasource.annotation.Master;
import com.yuanxiaotu.config.datasource.annotation.Slave;
import com.yuanxiaotu.entity.User;
import com.yuanxiaotu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaotianyu
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String SUCCESS = "success";

    private final UserMapper userMapper;

    @Autowired
    public UserController(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Master
    @GetMapping("/master")
    public String master() {
        userMapper.insert(User.builder().name(Math.random() * 100 + "_master").build());
        return SUCCESS;
    }

    @Slave
    @GetMapping("/slave")
    public String slave() {
        userMapper.insert(User.builder().name(Math.random() * 100 + "_slave").build());
        return SUCCESS;
    }
}
