package com.jwt.project.controller;


import com.jwt.project.dto.UserDto;
import com.jwt.project.entity.User;
import com.jwt.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRolesAndUsers() {
        userService.initRolesAndUser();
    }


    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }


    @GetMapping("/finduserbyname/{username}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        UserDto getById = userService.getUserByUsername(username);
        return new ResponseEntity<UserDto>(getById, HttpStatus.OK);
    }


    @GetMapping({"/viewAll"})
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> viewAll = userService.getAllUsers();
        return new ResponseEntity<List<UserDto>>(viewAll, HttpStatus.ACCEPTED);

    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<UserDto> updateUser(@PathVariable String username, @RequestBody UserDto updatedUser) {
        UserDto updateUser = userService.updateUser(username, updatedUser);
        return new ResponseEntity<>(updateUser, HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new ResponseEntity<String>("User is deleted successfully.", HttpStatus.OK);
    }
}
