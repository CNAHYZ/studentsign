package top.flytop.studentsign.controller.sysadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.service.AdminService;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName adminController
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 08/04/2019 09:46
 * @Version 1.0
 */
@Controller
@RequestMapping("sysadmin/")
public class adminController {
    private AdminService adminService;

    @Autowired
    private void adminService(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * @param
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 获取普通管理员列表
     * @Date 08/04/2019 14:40
     */
    @ResponseBody
    @RequestMapping("getAdminList")
    BaseResult getAdminList() {
        return adminService.getAdminList();
    }

    /**
     * @param
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 获取普通管理员
     * @Date 08/04/2019 14:40
     */
    @ResponseBody
    @RequestMapping(value = "getAdmin", method = RequestMethod.POST)
    BaseResult getAdmin(String username) {
        return adminService.getAdmin(username);
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 添加普通管理员
     * @Date 08/04/2019 14:40
     */
    @ResponseBody
    @RequestMapping(value = "addAdmin", method = RequestMethod.POST)
    BaseResult addAdmin(HttpServletRequest request) {
        return adminService.addAdmin(request);
    }

    /**
     * @param username
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 删除普通管理员
     * @Date 08/04/2019 14:40
     */
    @ResponseBody
    @RequestMapping(value = "removeAdmin", method = RequestMethod.POST)
    BaseResult removeAdmin(String username) {
        return adminService.removeAdmin(username);

    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 更新普通管理员
     * @Date 08/04/2019 14:40
     */
    @ResponseBody
    @RequestMapping(value = "updateAdmin", method = RequestMethod.POST)
    BaseResult updateAdmin(HttpServletRequest request) {
        return adminService.updateAdmin(request);
    }

}
