package com.man.ssoserver.Service;

import com.man.ssoserver.dao.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User getUserById(String id);
    public User getUserByName(String name);

}
