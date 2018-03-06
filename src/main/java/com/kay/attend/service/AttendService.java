package com.kay.attend.service;

import com.kay.attend.entity.Attend;
import com.kay.attend.vo.QueryCondition;
import com.kay.common.pape.PageQueryBean;

/**
 * Created by kay on 2018/3/5.
 */
public interface AttendService {

    void sign(Attend attend);

    PageQueryBean getAttendList(QueryCondition condition);

    void checkAttend();
}
