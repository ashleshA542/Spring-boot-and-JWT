package com.jwt.project.service;

import com.jwt.project.dto.UserDto;
import com.jwt.project.entity.Role;
import com.jwt.project.entity.User;
import com.jwt.project.exception.ResourceNotFoundException;
import com.jwt.project.repository.RoleRepository;
import com.jwt.project.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    public User registerNewUser(User user){
        Role role=roleRepository.findById("User").get();

        Set<Role> userroles = new HashSet<>();
        userroles.add(role);
        user.setRole(userroles);
        user.setUserPassword(getEncodePassword(user.getUserPassword()));
        return  userRepository.save(user);
    }


    public void initRolesAndUser(){

        Role adminRole=new Role();
        adminRole.setRoleName("Admin");
        adminRole.setDescription("Manage");
        roleRepository.save(adminRole);


        Role userRole= new Role();
        userRole.setRoleName("User");
        userRole.setDescription("Access System");
        roleRepository.save(userRole);



        User adminUser= new User();
        // adminUser.setId(1L);
        adminUser.setUserName("admin123");
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        adminUser.setUserPassword(getEncodePassword("admin123"));

        Set<Role> adminRoles=new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userRepository.save(adminUser);


    }

public String getEncodePassword(String password){
        return passwordEncoder.encode(password);

}


    public List<UserDto> getAllUsers() {
        List<User> findAll = (List<User>) userRepository.findAll();
        List<UserDto> findAllDto= findAll.stream().map(supplier -> modelMapper.map(supplier,UserDto.class)).collect(Collectors.toList());
        return  findAllDto ;

    }



    public UserDto getUserByUsername(String username) {
        User findBYuser = userRepository.findById(username).orElseThrow(()->new ResourceNotFoundException(username + " is not found"));;
            return modelMapper.map(findBYuser, UserDto.class);

    }





    public UserDto updateUser(String username, UserDto updatedUser) {
        User user=userRepository.findById(username).orElseThrow(()->new ResourceNotFoundException(username + " is not found"));
        user.setUserName(updatedUser.getUserFirstName());
        user.setUserLastName(updatedUser.getUserLastName());
        user.setUserName(updatedUser.getUserName());
        user.setUserPassword(updatedUser.getUserPassword());
        User save=userRepository.save(user);
        return modelMapper.map(save,UserDto.class);
    }



    public void deleteUser(String username) {
        User userById=userRepository.findById(username).orElseThrow(()->new ResourceNotFoundException(username + " is not found"));
        userRepository.delete(userById);

    }


    }



















