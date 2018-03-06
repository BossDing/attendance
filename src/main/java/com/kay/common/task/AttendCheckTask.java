package com.kay.common.task;

import com.kay.attend.service.AttendService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by kay on 2018/3/6.
 * 定时任务task
 */
public class AttendCheckTask {

    @Autowired
    private AttendService attendService;

    public void checkAttend(){
        attendService.checkAttend();
    }

}
