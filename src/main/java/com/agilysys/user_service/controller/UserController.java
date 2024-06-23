package com.agilysys.user_service.controller;

import com.agilysys.user_service.model.UserEntity;
import com.agilysys.user_service.service.UserService;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/user/createuser")
    UserEntity createUser(@RequestBody UserEntity user) {
       return userService.createUser(user);
    }

    @PostMapping(value = "/user/startjob")
    void startJob(){
        userService.startJob();
    }

    @GetMapping(value = "/user/download/{jobId}")
    public ResponseEntity<byte[]> download(@PathVariable("jobId") String jobId) {
        return userService.download(jobId);
    }

}
