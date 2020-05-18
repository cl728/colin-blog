package fun.donglin.service.impl;

import fun.donglin.entity.User;
import fun.donglin.mapper.UserMapper;
import fun.donglin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {
        User record = new User();
        record.setUsername( username );
        record.setPassword( password );
        return userMapper.selectOne( record );
    }
}
