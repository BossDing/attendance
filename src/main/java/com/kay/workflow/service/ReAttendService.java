package com.kay.workflow.service;

import com.kay.workflow.entity.ReAttend;

import java.util.List;

/**
 * Created by kay on 2018/3/6.
 * 补签服务
 */
public interface ReAttendService {

    void startReAttendFlow(ReAttend reAttend);

    List<ReAttend> listTasks(String userName);

    void approve(ReAttend reAttend);

    List<ReAttend> listReAttend(String username);
}
