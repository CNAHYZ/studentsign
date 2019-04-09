package top.flytop.studentsign.controller.sysadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.service.AdminService;
import top.flytop.studentsign.service.UserService;

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

    private UserService userService;

    @Autowired
    private void adminService(UserService userService) {
        this.userService = userService;
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
        return adminService.updateAdminInfo(request);
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 更新普通管理员密码
     * @Date 08/04/2019 14:40
     */
    @ResponseBody
    @RequestMapping(value = "updateAdminPwd", method = RequestMethod.POST)
    BaseResult updateAdminPwd(HttpServletRequest request) {
        return adminService.updateAdminPwd(request);
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 管理员修改自身密码的方法
     * @Date 2019/2/19 17:20
     */
    @RequestMapping(value = "changePwd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult changePwd(HttpServletRequest request) {
        return userService.changePwd(request);
    }

}
