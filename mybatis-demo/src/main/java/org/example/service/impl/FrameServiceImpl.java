package org.example.service.impl;

import org.example.mapper.FrameUserMapper;
import org.example.pojo.FrameUser;
import org.example.service.FrameUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrameServiceImpl implements FrameUserService {

    @Autowired
    FrameUserMapper  frameUserMapper;

    @Override
    public List<FrameUser> getAllUsers() {
        List<FrameUser> allUser = frameUserMapper.getAllUser();
        return allUser;
    }
}
