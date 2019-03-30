package top.flytop.studentsign.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.pojo.OpenTime;
import top.flytop.studentsign.service.SettingService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName SettingController
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/3/23 12:40
 * @Version 1.0
 */
@RequestMapping("/admin")
@Controller
public class SettingController {
    private SettingService settingService;

    @Autowired
    public void settingService(SettingService settingService) {
        this.settingService = settingService;
    }

    /**
     * @param openTime
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/3/23 17:42
     */
    @RequestMapping(value = "openTimeSetting", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult openTimeSetting(OpenTime openTime, HttpServletRequest request) {
        BaseResult result = settingService.saveOpenTime(openTime, request);
        return result;
    }

    /**
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 获取签到开放时间
     * @Date 2019/3/23 17:42
     */
    @RequestMapping(value = "getOpenTime", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getOpenTime(HttpServletRequest request) {
        Map result = settingService.getOpenTime();
        System.out.println(request.getSession().getServletContext().getAttribute("whetherSign"));
        return BaseResult.success(result);
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/3/29 22:25
     */
    @RequestMapping(value = "getOpenStatus", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getOpenStatus(HttpServletRequest request) {
        return BaseResult.success(request.getSession().getServletContext().getAttribute("whetherSign"));
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/3/29 22:25
     */
    @RequestMapping(value = "openStatusSetting", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult openStatusSetting(HttpServletRequest request) {
        boolean openStatus = Boolean.valueOf(request.getParameter("openStatus"));
        request.getSession().getServletContext().setAttribute("whetherSign", openStatus);
        return BaseResult.success(openStatus ? "已开启当前时间的签到功能" : "已关闭当前时间的签到功能");
    }


}
