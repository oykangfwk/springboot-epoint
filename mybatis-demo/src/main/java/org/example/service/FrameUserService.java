package org.example.service;

import org.example.pojo.FrameUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrameUserService {

    public List<FrameUser> getAllUsers();
}
