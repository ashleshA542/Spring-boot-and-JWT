package com.jwt.project.config;

import com.jwt.project.service.JwtService;
import com.jwt.project.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
   private  JwtService jwtService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //get jwt token from request

        final  String requestTokenHeader=request.getHeader("Authorization");
        String username=null;  // username is fetched from jwtutil
        String jwtToken = null;

        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken= requestTokenHeader.substring(7);

            try{
                username=jwtUtil.getUserNameFromToken(jwtToken);

            }catch (IllegalArgumentException e){
                System.out.println("unable to get JWT token");

            }catch (ExpiredJwtException e){
                System.out.println("Jwt token is expired");

            }catch (MalformedJwtException e){
                System.out.println("invalid jwt");
            }

        }else {
            System.out.println("Jwt token does not begin with bearer");
        }


        //once we get the token,we validate


        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {

            UserDetails userDetails= jwtService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            }


        filterChain.doFilter(request,response);

    }
}
