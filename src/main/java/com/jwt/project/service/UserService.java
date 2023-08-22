package com.jwt.project.service;

import com.jwt.project.entity.Role;
import com.jwt.project.entity.User;
import com.jwt.project.repository.RoleRepository;
import com.jwt.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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



/*
        User user= new User();
        //user.setId(2L);
        user.setUserName("ashlesha123");
        user.setUserFirstName("ashlesha");
        user.setUserLastName("basnet");
        user.setUserPassword(getEncodePassword("12345"));

        Set<Role> userRoles = new HashSet<>();
        user.setRole(userRoles);
        userRoles.add(userRole);
        userRepository.save(user);*/


    }

public String getEncodePassword(String password){
        return passwordEncoder.encode(password);

}


}
