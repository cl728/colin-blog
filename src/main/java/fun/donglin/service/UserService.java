package fun.donglin.service;

import fun.donglin.entity.User;

public interface UserService {

    User checkUser(String username, String password);

}
