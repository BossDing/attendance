package com.kay.workflow.service.impl;

import com.kay.attend.dao.AttendMapper;
import com.kay.attend.entity.Attend;
import com.kay.workflow.dao.ReAttendMapper;
import com.kay.workflow.entity.ReAttend;
import com.kay.workflow.service.ReAttendService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kay on 2018/3/6.
 */
@Service
public class ReAttendServiceImpl implements ReAttendService {


    /**
     * 补签流程状态
     */
    private static final Byte RE_ATTEND_STATUS_ONGOING =1 ;
    private static final Byte RE_ATTEND_STATUS_PSSS =2 ;
    private static final Byte RE_ATTEND_STATUS_REFUSE =3 ;
    /**
     * 任务关联补签数据键
     */
    private static final String RE_ATTEND_SIGN = "re_attend";

    /**
     * 流程下一步处理人
     */
    private static final String NEXT_HANDLER = "next_handler";
    /**
     *
     */
    private static final java.lang.String RE_ATTEND_FLOW_ID = "re_attend";

    private static final Byte ATTEND_STATUS_NORMAL = 1;


    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ReAttendMapper reAttendMapper;

    @Autowired
    private AttendMapper attendMapper;

    /**
     * 开启补签工作流
     * @param reAttend
     */
    @Override
    public void startReAttendFlow(ReAttend reAttend) {
        //组长应去数据库拿
        reAttend.setCurrentHandler("测试组长1");
        reAttend.setStatus(RE_ATTEND_STATUS_ONGOING);
        //将补签记录添加到补签表
        reAttendMapper.insertSelective(reAttend);
        //补签的参数
        Map<String, Object> map = new HashedMap();
        map.put(RE_ATTEND_SIGN, reAttend);
        map.put(NEXT_HANDLER, reAttend.getCurrentHandler());
        //启动补签流程实例
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(RE_ATTEND_FLOW_ID, map);
        //提交用户补签任务
        Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
        taskService.complete(task.getId(), map);

    }

    /**
     * 组长查看
     * @param userName
     * @return
     */
    @Override
    public List<ReAttend> listTasks(String userName) {
        List<ReAttend> reAttendList = new ArrayList<>();
        List<Task> taskList= taskService.createTaskQuery().processVariableValueEquals(userName).list();
        //转换成页面实体
        if(CollectionUtils.isNotEmpty(taskList)){
            for(Task task : taskList){
                Map<String, Object> variable = taskService.getVariables(task.getId());
                ReAttend reAttend = (ReAttend)variable.get(RE_ATTEND_SIGN);
                reAttend.setTaskId(task.getId());
                reAttendList.add(reAttend);
            }
        }
        return reAttendList;
    }

    /**
     * 审批
     * @param reAttend
     */
    @Override
    public void approve(ReAttend reAttend) {
        Task task =  taskService.createTaskQuery().taskId(reAttend.getTaskId()).singleResult();

        if((""+RE_ATTEND_STATUS_PSSS).equals(reAttend.getApproveFlag())){
            //审批通过 修改补签数据状态
            //修改相关考勤数据 考勤状态改为正常
            Attend attend = new Attend();
            attend.setId(reAttend.getAttendId());
            attend.setAttendStatus(ATTEND_STATUS_NORMAL);
            attendMapper.updateByPrimaryKeySelective(attend);
            reAttend.setStatus(RE_ATTEND_STATUS_PSSS);
            reAttendMapper.updateByPrimaryKeySelective(reAttend);
        }else if((""+RE_ATTEND_STATUS_REFUSE).equals(reAttend.getApproveFlag())){
            reAttend.setStatus(RE_ATTEND_STATUS_REFUSE);
            reAttendMapper.updateByPrimaryKeySelective(reAttend);
        }
        taskService.complete(reAttend.getTaskId());
    }

    /**
     * 查看补签
     * @param username
     * @return
     */
    @Override
    public List<ReAttend> listReAttend(String username) {
        List<ReAttend> list =reAttendMapper.selectReAttendRecord(username);
        return list;
    }
}
