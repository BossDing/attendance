package com.kay.attend.service.impl;

import com.kay.attend.dao.AttendMapper;
import com.kay.attend.entity.Attend;
import com.kay.attend.service.AttendService;
import com.kay.common.uits.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by kay on 2018/3/5.
 */
@Service("attendServiceImpl")
public class AttendServiceImpl implements AttendService {

    private static final int NOON_HOUR = 12;  //中午小时

    private static final int NOON_MINUTE = 00; //中午分钟

    @Autowired
    private AttendMapper attendMapper;

    /**
     * 打卡业务
     * @param attend
     */
    @Override
    public void sign(Attend attend) {

        Date today = new Date();
        attend.setAttendDate(today);
        attend.setAttendWeek((byte) DateUtils.getWeekOfToday());

        Date noon = DateUtils.getDate(NOON_HOUR, NOON_MINUTE);   //获取中午12点的date对象

        //先判断是否今天有打卡,有打卡就将其取出来
        Attend recordOfToday = attendMapper.getTodayRecord(attend.getUserId());

        if(recordOfToday == null){
            //如果未打卡，则判断然后打卡
            if(today.compareTo(noon)<=0){
                //如果早于12点签到
                attend.setAttendMorning(today);
            }else {
                //晚于12点签到
                attend.setAttendEvening(today);
            }
            attendMapper.insertSelective(attend);
        }else {
            //如果存在则表示已经打卡
            if(today.compareTo(noon)<=0){
                //如果早于12点签到
                return;
            }else {
                //晚于12点签到
                recordOfToday.setAttendEvening(today);
            }
            //更新打卡时间
            attendMapper.updateByPrimaryKeySelective(recordOfToday);
        }




    }
}
