package com.ppmtool.security;

import com.ppmtool.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.ppmtool.security.SecurityConstants.SECRET;
import static com.ppmtool.security.SecurityConstants.TOKEN_EXPIRATION_TIME;

@Component
public class JwtTokenProvider {

    //Generate the token with valid username & pw
    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal(); //get the user from the auth obj
        Date now = new Date(System.currentTimeMillis());

        Date expirtyDate = new Date(now.getTime()+ TOKEN_EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername()); //adds the claims stuff to the token
        claims.put("fullName", user.getFullName());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setExpiration(expirtyDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
    //Validate the token

    //Get user Id from token


}
