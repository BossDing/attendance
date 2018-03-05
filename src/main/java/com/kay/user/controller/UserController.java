package com.kay.user.controller;

import com.kay.user.entity.User;
import com.kay.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by kay on 2017/7/6.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping("/userinfo")
    @ResponseBody
    public User getUserInfo(HttpServletRequest request){
        User userinfo = (User)request.getSession().getAttribute("userinfo");
        return userinfo;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }
}
