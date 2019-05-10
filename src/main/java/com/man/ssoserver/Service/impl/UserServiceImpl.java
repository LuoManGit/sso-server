package com.man.ssoserver.Service.impl;

import com.man.ssoserver.Service.UserService;
import com.man.ssoserver.dao.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(String id){
        User user = null;
        if(id.equals("2019")){
            user = new User();
            user.setId("2019");
            user.setName("luoman");
        }
        return user;
    }
    @Override
    public User getUserByName(String name){
        User user = null;
        if(name.equals("luoman")){
            user = new User();
            user.setId("2019");
            user.setName("luoman");
        }
        return user;
    }



}
