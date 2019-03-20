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
    BaseResult getLeave(String dayNum);

    BaseResult getLeaveByStatus(String status);

    BaseResult removeLeave(Integer id);

    BaseResult getLeaveDetailById(Integer id);

    BaseResult examineLeave(Leave leave);

    Boolean addLeave(HttpServletRequest request);

    List<Leave> getLeaveBySNo(String sNo, Integer auditStatus);

    BaseResult removeLeaveForS(Integer id);
}
