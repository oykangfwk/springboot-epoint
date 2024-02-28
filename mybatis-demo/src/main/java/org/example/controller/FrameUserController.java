package org.example.controller;

import org.example.pojo.FrameUser;
import org.example.service.FrameUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FrameUserController {

    @Autowired
    FrameUserService frameUserService;

    @GetMapping("/getalluser")
    public List<FrameUser> getAllUsers(){
        List<FrameUser> allUsers = frameUserService.getAllUsers();
        return allUsers;
    }
}
