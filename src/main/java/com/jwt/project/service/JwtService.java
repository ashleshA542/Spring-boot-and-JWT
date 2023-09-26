package com.jwt.project.service;

import com.jwt.project.entity.JwtRequest;
import com.jwt.project.entity.JwtResponse;
import com.jwt.project.entity.User;
import com.jwt.project.exception.ResourceNotFoundException;
import com.jwt.project.repository.UserRepository;
import com.jwt.project.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private  UserRepository userRepository;

    @Autowired
   private  AuthenticationManager authenticationManager;



    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
       String userName=jwtRequest.getUserName();
       String userPassword=jwtRequest.getUserPassword();
       authenticate(userName,userPassword);

       UserDetails userDetails=loadUserByUsername(userName);
      String newGeneratedToken= jwtUtil.generateToken(userDetails);

      User user= userRepository.findById(userName).get();
      return new JwtResponse(user,newGeneratedToken);

   }





    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //loading user from database by username
       User user= this.userRepository.findById(username).get();



        if (user!= null){
            return new org.springframework.security.core.userdetails.User(
                   user.getUserName(),
                   user.getUserPassword(),
                    getAuthorities(user)
            );


        }else {
            throw new UsernameNotFoundException("Username is not valid");


        }

    }

    private Set getAuthorities(User user){
       Set<SimpleGrantedAuthority> authorities= new HashSet<>();
       user.getRole().forEach(role -> {
           authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
       });
       return authorities;
    }


    private void authenticate(String userName,String userPassword) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,userPassword));

        }catch (DisabledException e){
            throw new Exception("user is disabled",e);
        }catch (BadCredentialsException e){
            throw new Exception("Bad credentials from user",e);
        }


    }
}

