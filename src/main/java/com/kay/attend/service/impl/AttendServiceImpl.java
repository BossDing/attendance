package com.kay.attend.service.impl;

import com.kay.attend.dao.AttendMapper;
import com.kay.attend.entity.Attend;
import com.kay.attend.service.AttendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kay on 2018/3/5.
 */
@Service("attendServiceImpl")
public class AttendServiceImpl implements AttendService {

    @Autowired
    private AttendMapper attendMapper;

    @Override
    public void sign(Attend attend) {
        attendMapper.insertSelective(attend);
    }
}
