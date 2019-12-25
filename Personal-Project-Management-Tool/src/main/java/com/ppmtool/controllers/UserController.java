package com.ppmtool.controllers;

import com.ppmtool.domain.User;
import com.ppmtool.services.MapValidationErrorService;
import com.ppmtool.services.UserService;
import com.ppmtool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
        //Validate passwords match
        userValidator.validate(user,result);
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap.getStatusCode()== HttpStatus.BAD_REQUEST) return errorMap;


        User newUser = userService.saveUser(user);
        newUser.setConfirmPassword(""); // hide the confirm pw in response
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

    }
}
