package top.flytop.studentsign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.mapper.LeaveMapper;
import top.flytop.studentsign.pojo.Leave;
import top.flytop.studentsign.service.LeaveService;
import top.flytop.studentsign.utils.FileUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName LeaveServiceImpl
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/27 13:51
 * @Version 1.0
 */
@Service
public class LeaveServiceImpl implements LeaveService {
    private LeaveMapper leaveMapper;

    @Autowired
    private void leaveMapper(LeaveMapper leaveMapper) {
        this.leaveMapper = leaveMapper;
    }

    /**
     * @param dayNum
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 请假列表/天数筛选
     * @date 2019/1/27 19:50
     */
    @Override
    public List<Leave> getLeave(String dayNum) {
        List list = leaveMapper.getLeave(dayNum);
        return list;
    }

    /**
     * @param status
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 根据状态筛选
     * @date 2019/1/27 19:50
     */
    @Override
    public List<Leave> getLeaveByStatus(String status) {
        List list = leaveMapper.getLeaveByStatus(status);
        return list;
    }

    /**
     * @param startTime
     * @param endTime
     * @return java.util.List<top.flytop.studentsign.pojo.Leave>
     * @Description TODO 根据自定义时间筛选签到记录
     * @Date 20/04/2019 21:08
     */
    @Override
    public List<Leave> getLeaveByTime(String startTime, String endTime) {
        List<Leave> list = leaveMapper.getLeaveByTime(startTime, endTime);
        return list;
    }

    /**
     * @param id
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 删除请假记录
     * @date 2019/1/27 20:37
     */
    @Override
    public BaseResult removeLeave(Integer id) {
        Boolean delResult = leaveMapper.removeLeaveById(id);
        if (delResult)
            return new BaseResult<String>(true, "删除成功！");
        else return new BaseResult(false, 1, "删除失败，请重试！");
    }

    /**
     * @param id
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 请假详情
     * @date 2019/1/27 20:37
     */
    @Override
    public BaseResult getLeaveDetailById(Integer id) {
        Leave leaveDetail = leaveMapper.getLeaveDetailById(id);
        if (leaveDetail != null)
            return new BaseResult<>(true, leaveDetail);
        else return new BaseResult(false, 1, "没有该条信息！");
    }

    /**
     * @param leave
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/28 15:43
     */
    @Override
    public BaseResult examineLeave(Leave leave) {
        Boolean updateResult = leaveMapper.updateAuditStatus(leave);
        if (updateResult) {
            if (leave.getAuditStatus() == 1)
                return new BaseResult<>(true, "已同意该生请假！");
            else
                return new BaseResult<>(true, "已拒绝该生请假！");
        } else return new BaseResult(false, 1, "操作失败，请重试！");
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 学生请假
     * @Date 2019/2/11 16:03
     */
    @Override
    public Boolean addLeave(HttpServletRequest request) {
        HttpSession session = request.getSession();
        BaseResult saveResult =
                FileUtil.saveFileByReq(request, null, "attachment", "/leaveFile/");
        String attachment = null;
        if (saveResult.isSuccess()) {
            attachment = String.valueOf(saveResult.getData());
        } else
            return false;
        Integer category = Integer.valueOf(request.getParameter("category"));
        Timestamp startTime = Timestamp.valueOf(request.getParameter("startTime"));
        Timestamp endTime = Timestamp.valueOf(request.getParameter("endTime"));
        String comment = request.getParameter("comment");
        Timestamp applicationTime = new Timestamp(System.currentTimeMillis());
        String sNo = String.valueOf(session.getAttribute("currentUser"));

        Leave leave = new Leave();

        leave.setsNo(sNo);
        leave.setCategory(category);
        leave.setApplicationTime(applicationTime);
        leave.setStartTime(startTime);
        leave.setEndTime(endTime);
        leave.setComment(comment);
        leave.setAttachment(attachment);
        System.out.println(leave);
        return leaveMapper.addLeave(leave);
    }

    /**
     * @param sNo
     * @param auditStatus
     * @return java.util.List<top.flytop.studentsign.pojo.Leave>
     * @Description TODO 获取某生的请假记录
     * @Date 2019/2/13 18:59
     */
    @Override
    public List<Leave> getLeaveBySNo(String sNo, Integer auditStatus) {
        return leaveMapper.getLeaveBySNo(sNo, auditStatus);
    }

    /**
     * @param id
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 学生删除请假操作，需检查是否已审批
     * @Date 2019/2/13 18:58
     */
    @Override
    public BaseResult removeLeaveForS(Integer id) {
        Leave leave = leaveMapper.getLeaveDetailById(id);
        if (leave.getAuditStatus() != 0)
            return BaseResult.fail(1, "删除失败，仅能删除未审批的申请！");
        Boolean delResult = leaveMapper.removeLeaveById(id);
        if (delResult)
            return BaseResult.success("删除成功！");
        else return BaseResult.fail(1, "删除失败，请重试！");
    }

    /**
     * @param keyword
     * @return java.util.List<top.flytop.studentsign.pojo.Leave>
     * @Description TODO 关键词搜索
     * @Date 20/04/2019 21:05
     */
    @Override
    public List<Leave> getLeaveListByKeyword(String keyword) {
        return leaveMapper.getLeaveListByKeyword(keyword);
    }

}
