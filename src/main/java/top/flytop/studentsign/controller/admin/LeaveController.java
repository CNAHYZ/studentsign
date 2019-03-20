package top.flytop.studentsign.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.pojo.Leave;
import top.flytop.studentsign.service.LeaveService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName LeaveController
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/27 13:50
 * @Version 1.0
 */
@Controller
@RequestMapping("admin/")
public class LeaveController {
    private LeaveService leaveService;

    @Autowired
    private void leaveService(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    /**
     * @param dayNum
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/27 19:52
     */
    @RequestMapping("getLeave")
    @ResponseBody
    public BaseResult getLeave(String dayNum) {
        return leaveService.getLeave(dayNum);
    }

    /**
     * @param auditStatus
     * @param sNo
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/3/20 22:22
     */
    @RequestMapping("getLeaveBySNo")
    @ResponseBody
    public BaseResult getLeaveBySNo(@RequestParam(defaultValue = "") Integer auditStatus, String sNo, HttpServletRequest request) {
        return new BaseResult<>(true, leaveService.getLeaveBySNo(sNo, auditStatus));
    }

    /**
     * @param param
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/27 19:52
     */
    @RequestMapping("leaveStatusFilter")
    @ResponseBody
    public BaseResult leaveStatusFilter(String param) {
        return leaveService.getLeaveByStatus(param);
    }

    /**
     * @param id
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/27 20:30
     */
    @RequestMapping("removeLeave")
    @ResponseBody
    public BaseResult removeLeave(Integer id) {
        return leaveService.removeLeave(id);
    }

    /**
     * @param id
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/28 15:23
     */
    @RequestMapping("getLeaveDetail")
    @ResponseBody
    public BaseResult getLeaveDetail(Integer id) {
        return leaveService.getLeaveDetailById(id);
    }

    /**
     * @param id
     * @param status
     * @param refusalReason
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/28 21:58
     */
    @RequestMapping("examineLeave")
    @ResponseBody
    public BaseResult examineLeave(Integer id, Integer status, String refusalReason) {
        Leave leave = new Leave();
        leave.setId(id);
        leave.setAuditStatus(status);
        if (status == 2)
            leave.setRefusalReason(refusalReason);
        System.out.println(leave);
        return leaveService.examineLeave(leave);
    }

    /**
     * @param
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/28 21:58
     */
    @RequestMapping("getNoExamineLeave")
    @ResponseBody
    public BaseResult getNoExamineLeave(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "1") Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        BaseResult filterResult = leaveService.getLeaveByStatus("0");
        if (filterResult.isSuccess()) {
            List<Leave> leaveList = (List) filterResult.getData();
            PageInfo<Leave> page = new PageInfo<>(leaveList);
            return new BaseResult<>(true, page);
        } else
            return new BaseResult(false, 1, "暂无信息");
    }

}
