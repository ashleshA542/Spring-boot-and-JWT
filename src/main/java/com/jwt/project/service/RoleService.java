package com.jwt.project.service;

import com.jwt.project.entity.Role;
import com.jwt.project.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createNewRole(Role role){

        return roleRepository.save(role);



    }


}
