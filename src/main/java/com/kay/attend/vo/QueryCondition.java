package com.kay.attend.vo;

import com.kay.common.pape.PageQueryBean;

/**
 * Created by kay on 2018/3/6.
 */
public class QueryCondition extends PageQueryBean{

    private Long userId;        //用户id

    private String startDate;  //查询起始时间

    private String endDate;    //查询结束时间

    private String rangeDate;  //时间范围--对应日期插件的使用方式

    private String attendStatus;  //查询条件-考勤状态

    public String getAttendStatus() {
        return attendStatus;
    }

    public void setAttendStatus(String attendStatus) {
        this.attendStatus = attendStatus;
    }

    public String getRangeDate() {
        return rangeDate;
    }

    public void setRangeDate(String rangeDate) {
        this.rangeDate = rangeDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
