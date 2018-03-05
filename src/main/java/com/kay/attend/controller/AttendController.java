package com.kay.attend.controller;

import com.kay.attend.entity.Attend;
import com.kay.attend.service.AttendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kay on 2018/3/5.
 *
 * 考勤Controller
 *
 */
@Controller
public class AttendController {

    @Autowired
    private AttendService attendService;

    @RequestMapping("/attend")
    public String attend(){
        return "attend";
    }

    @RequestMapping("/sign")
    @ResponseBody
    public String sign(@RequestBody Attend attend){
        attendService.sign(attend);
        return "succ";
    }

}
