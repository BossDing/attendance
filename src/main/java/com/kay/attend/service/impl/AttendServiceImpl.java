package com.kay.attend.service.impl;

import com.kay.attend.dao.AttendMapper;
import com.kay.attend.entity.Attend;
import com.kay.attend.service.AttendService;
import com.kay.attend.vo.QueryCondition;
import com.kay.common.pape.PageQueryBean;
import com.kay.common.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kay on 2018/3/5.
 */
@Service("attendServiceImpl")
public class AttendServiceImpl implements AttendService {

    /**
     * 上午下午界限时间
     */
    private static final int NOON_HOUR = 12;  //中午小时
    private static final int NOON_MINUTE = 00; //中午分钟
    /**
     * 正常早晚打卡时间
     */
    private static final int MORNING_HOUR = 9;
    private static final int MORNING_MINUTE = 00;
    private static final int EVENING_HOUR = 6;
    private static final int EVENING_MINUTE = 00;

    /**
     * 异常状态
     */
    private static final Byte ATTEND_STATUS_ABNORMAL = 2;
    private static final Byte ATTEND_STATUS_NORMAL = 1;
    /**
     * 异常时间
     */
    private static final Integer ABSENCE_DAY = 480;

    @Autowired
    private AttendMapper attendMapper;

    /**
     * 查询签到记录分页列表
     * @param condition
     * @return
     */
    @Override
    public PageQueryBean getAttendList(QueryCondition condition) {

        int count=attendMapper.getCountByCondition(condition);

        PageQueryBean resultBean = new PageQueryBean();

        //有记录再取查询分页
        if (count > 0) {
            List<Attend> attendList=attendMapper.getListByCondition(condition);
            resultBean.setTotalRows(count);
            resultBean.setCurrentPage(condition.getCurrentPage());
            resultBean.setStartRow(condition.getStartRow());
            resultBean.setItems(attendList);
        }

        return resultBean;
    }

    /**
     * 检查异常考勤
     */
    @Override
    public void checkAttend() {
        //查询缺勤用户ID 插入打卡记录  并且设置为异常 缺勤480分钟
        List<Long> userIdList =attendMapper.selectTodayAbsence();
        if(CollectionUtils.isNotEmpty(userIdList)){
            List<Attend> attendList = new ArrayList<Attend>();
            for(Long userId:userIdList){
                Attend attend = new Attend();
                attend.setUserId(userId);
                attend.setAttendDate(new Date());
                attend.setAttendWeek((byte)DateUtils.getWeekOfToday());
                attend.setAbsence(ABSENCE_DAY);
                attend.setAttendStatus(ATTEND_STATUS_ABNORMAL);
                attendList.add(attend);
            }
            attendMapper.batchInsert(attendList);
        }
        // 检查晚打卡 将下班未打卡记录设置为异常
        List<Attend> absenceList = attendMapper.selectTodayEveningAbsence();
        if(CollectionUtils.isNotEmpty(absenceList)){
            for(Attend attend : absenceList){
                attend.setAbsence(ABSENCE_DAY);
                attend.setAttendStatus(ATTEND_STATUS_ABNORMAL);
                attendMapper.updateByPrimaryKeySelective(attend);
            }
        }
    }

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
        Date morningDate = DateUtils.getDate(MORNING_HOUR, MORNING_MINUTE);
        Date eveningDate = DateUtils.getDate(EVENING_HOUR, EVENING_MINUTE);

        //先判断是否今天有打卡,有打卡就将其取出来
        Attend recordOfToday = attendMapper.getTodayRecord(attend.getUserId());

        if(recordOfToday == null){
            //如果未打卡，则判断然后打卡
            if(today.compareTo(noon)<=0){
                //如果早于12点签到
                attend.setAttendMorning(today);
                if (today.compareTo(morningDate) > 0) {
                    attend.setAbsence(DateUtils.getMinute(morningDate,today)); //迟到时间
                    attend.setAttendStatus(ATTEND_STATUS_ABNORMAL);   //考勤异常
                }

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
                //早退
                if(today.compareTo(eveningDate) <0){
                    recordOfToday.setAbsence(DateUtils.getMinute(today,eveningDate));  //早退多少分钟
                    recordOfToday.setAttendStatus(ATTEND_STATUS_ABNORMAL);  //考勤异常
                }else {
                    recordOfToday.setAttendStatus(ATTEND_STATUS_NORMAL);
                    recordOfToday.setAbsence(0);
                }
            }
            //更新打卡时间
            attendMapper.updateByPrimaryKeySelective(recordOfToday);
        }

    }
}
