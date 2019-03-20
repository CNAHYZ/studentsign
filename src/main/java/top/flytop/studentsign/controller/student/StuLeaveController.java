package top.flytop.studentsign.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.service.LeaveService;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LeaveController
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/27 13:50
 * @Version 1.0
 */
@Controller
@RequestMapping("student/")
public class StuLeaveController {
    private LeaveService leaveService;

    @Autowired
    private void leaveService(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/2/11 16:25
     */
    @RequestMapping(value = "askForLeave", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult askForLeave(HttpServletRequest request) {
        try {
            return leaveService.addLeave(request) ? new BaseResult<>(true, "申请成功，请等待审核！")
                    : new BaseResult<>(false, 1, "申请失败，请稍后重试！");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResult<>(false, 1, "申请失败，请稍后重试！");
        }
    }

    /**
     * @Description TODO
     * @param auditStatus
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Date 2019/2/11 21:34
     */
    @RequestMapping("getLeave")
    @ResponseBody
    public BaseResult getLeave(@RequestParam(defaultValue = "") Integer auditStatus, HttpServletRequest request) {
        String sNo = String.valueOf(request.getSession().getAttribute("currentUser"));
        return new BaseResult<>(true, leaveService.getLeaveBySNo(sNo, auditStatus));
    }


    /**
     * @param id
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/27 20:30
     */
    @RequestMapping(value = "removeLeave", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult removeLeave(Integer id) {
        return leaveService.removeLeaveForS(id);
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


}
