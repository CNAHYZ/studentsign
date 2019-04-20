package top.flytop.studentsign.service;

import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.pojo.Leave;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName LeaveService
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/27 13:54
 * @Version 1.0
 */

public interface LeaveService {
    List<Leave> getLeave(String dayNum);

    List<Leave> getLeaveByStatus(String status);

    List<Leave> getLeaveByTime(String startTime, String endTime);

    BaseResult removeLeave(Integer id);

    BaseResult getLeaveDetailById(Integer id);

    BaseResult examineLeave(Leave leave);

    Boolean addLeave(HttpServletRequest request);

    List<Leave> getLeaveBySNo(String sNo, Integer auditStatus);

    BaseResult removeLeaveForS(Integer id);

    List<Leave> getLeaveListByKeyword(String keyword);
}
