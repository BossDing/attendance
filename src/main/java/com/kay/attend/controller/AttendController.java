package com.kay.attend.controller;

import com.kay.attend.entity.Attend;
import com.kay.attend.service.AttendService;
import com.kay.attend.vo.QueryCondition;
import com.kay.common.pape.PageQueryBean;
import com.kay.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by kay on 2018/3/5.
 *
 * 考勤Controller
 *
 */
@Controller
@RequestMapping("/attend")
public class AttendController {

    @Autowired
    private AttendService attendService;

    @RequestMapping
    public String attend(){
        return "attend";
    }

    @RequestMapping("/sign")
    @ResponseBody
    public String sign(@RequestBody Attend attend){
        attendService.sign(attend);
        return "succ";
    }


    @RequestMapping("/attendList")
    @ResponseBody
    public PageQueryBean listAttend(QueryCondition condition, HttpSession session){
        User user= (User) session.getAttribute("userinfo");
        condition.setUserId(user.getId());

        String[] rangeDates = condition.getRangeDate().split("/");  //起止时间以 / 分割，此处应以前端插件为准
        condition.setStartDate(rangeDates[0]);
        condition.setEndDate(rangeDates[1]);

        PageQueryBean result = attendService.getAttendList(condition);
        return result;
    }

}
