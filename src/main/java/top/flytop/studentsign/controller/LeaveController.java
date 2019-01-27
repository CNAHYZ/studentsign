package top.flytop.studentsign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.service.LeaveService;

/**
 * @ClassName LeaveController
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/27 13:50
 * @Version 1.0
 */
@Controller
public class LeaveController {
    private LeaveService leaveService;

    @Autowired
    private void leaveService(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    /**
     * @param param
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/27 19:52
     */
    @RequestMapping("getLeave")
    @ResponseBody
    public BaseResult getLeave(String param) {
        return leaveService.getLeave(param);
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

    @RequestMapping("getLeaveDetail")
    @ResponseBody
    public BaseResult getLeaveDetail(Integer id) {
        return leaveService.getLeaveDetailById(id);
    }
}
