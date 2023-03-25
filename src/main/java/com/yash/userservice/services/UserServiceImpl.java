package com.yash.userservice.services;

import com.yash.userservice.dao.UserRepository;
import com.yash.userservice.dto.UserDTO;
import com.yash.userservice.entities.User;
import com.yash.userservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(UserDTO userDTO) {
        String userID = UUID.randomUUID().toString();
        User user = User.build(userID,userDTO.getUserName(),userDTO.getEmail(),userDTO.getAbout(),new ArrayList<>());
        user.setUserId(userID);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByID(String userId) {
        return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found :"+userId));
    }

    @Override
    public void deleteUserByID(String userId) {
        userRepository.deleteById(userId);
    }
}
