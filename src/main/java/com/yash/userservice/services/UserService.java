package com.yash.userservice.services;

import com.yash.userservice.dto.UserDTO;
import com.yash.userservice.entities.User;

import java.util.List;

public interface UserService {

    User addUser(UserDTO user);
    List<User> getAllUsers();
    User getUserByID(String userId);

    void deleteUserByID(String userId);
}
