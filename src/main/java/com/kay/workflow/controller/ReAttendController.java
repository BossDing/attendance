package com.kay.workflow.controller;

import com.kay.user.entity.User;
import com.kay.workflow.entity.ReAttend;
import com.kay.workflow.service.ReAttendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by kay on 2018/3/6.
 */

@Controller
@RequestMapping("reAttend")
public class ReAttendController {

    @Autowired
    private ReAttendService reAttendService;

    @RequestMapping
    public String toReAttend(Model model, HttpSession session) {
        User user= (User) session.getAttribute("userinfo");
        List<ReAttend> reAttendList = reAttendService.listReAttend(user.getUsername());
        model.addAttribute("reAttendList", reAttendList);
        return "reAttend";
    }

    @RequestMapping("/start")
    public void startReAttendFlow(@RequestBody ReAttend reAttend, HttpSession session) {
        User user = (User) session.getAttribute("userinfo");
        reAttend.setReAttendStarter(user.getRealName());
        reAttendService.startReAttendFlow(reAttend);
    }

    @RequestMapping("/list")
    public String listReAttendFlow(Model model, HttpSession session) {
        User user= (User) session.getAttribute("userinfo");
        String username = user.getUsername();
        List<ReAttend> reAttendList = reAttendService.listTasks(username);
        model.addAttribute("tasks", reAttendList);
        return "reAttendApprove";
    }

    @RequestMapping("/approve")
    public void approveReAttendFlow(@RequestBody ReAttend reAttend) {
        reAttendService.approve(reAttend);
    }


}
