package com.kay.attend.dao;

import com.kay.attend.entity.Attend;
import com.kay.attend.vo.QueryCondition;

import java.util.List;

public interface AttendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Attend record);

    int insertSelective(Attend record);

    Attend selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Attend record);

    int updateByPrimaryKey(Attend record);

    Attend getTodayRecord(Long userId);

    int getCountByCondition(QueryCondition condition);

    List<Attend> getListByCondition(QueryCondition condition);

    List<Long> selectTodayAbsence();

    void batchInsert(List<Attend> attendList);

    List<Attend> selectTodayEveningAbsence();
}