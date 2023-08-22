package com.jwt.project.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final String SECRET_KEY="hyashlesha";

    private  static  final  int TOKEN_VALIDITY= 3600*5; /*after what time token expires*/

    //retrieve username from jwt token
    public String getUserNameFromToken(String token){

        return  getClaimFromToken(token, Claims::getSubject);
    }


    private <T> T getClaimFromToken(String token, Function<Claims,T> claimResolver){

        final  Claims claims=getAllClaimsFromToken(token);
        return claimResolver.apply(claims);


    }

//for retrieving any information from token we need the secret key
    private  Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }




    public   boolean validateToken(String token, UserDetails userDetails){
        String userName=getUserNameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }



    private boolean isTokenExpired(String token){

        final Date expiratonDate=getExpirationDateFromToken(token);
        return expiratonDate.before(new Date());

    }

    //hamle token diyepaxi expiration date hamlai provide garxa
    private Date getExpirationDateFromToken(String token){

        return getClaimFromToken(token,Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails){

        Map<String,Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact()
                ;

    }



}