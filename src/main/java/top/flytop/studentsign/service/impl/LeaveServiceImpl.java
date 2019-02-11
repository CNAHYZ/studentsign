package top.flytop.studentsign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.mapper.LeaveMapper;
import top.flytop.studentsign.pojo.Leave;
import top.flytop.studentsign.service.LeaveService;
import top.flytop.studentsign.utils.ImageUtil;

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
     * @Description TODO
     * @date 2019/1/27 19:50
     */
    @Override
    public BaseResult getLeave(String dayNum) {
        List list = leaveMapper.getLeave(dayNum);
        System.out.println(list);
        return new BaseResult<>(true, list);
    }

    /**
     * @param status
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/27 19:50
     */
    @Override
    public BaseResult getLeaveByStatus(String status) {
        List list = leaveMapper.getLeaveByStatus(status);
        System.out.println(list);
        return new BaseResult<>(true, list);
    }

    /**
     * @param id
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
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
     * @Description TODO
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
     * @Description TODO
     * @Date 2019/2/11 16:03
     */
    @Override
    public Boolean askForLeave(HttpServletRequest request) {
        HttpSession session = request.getSession();
        BaseResult saveResult =
                ImageUtil.saveImgByReq(request, null, "attachment", "userUpload/leaveFile/");
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

    @Override
    public List<Leave> getLeaveBySNo(String sNo, Integer auditStatus) {
        return leaveMapper.getLeaveBySNo(sNo, auditStatus);
    }

}
