package top.flytop.studentsign.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.pojo.SClass;
import top.flytop.studentsign.service.ClassService;

/**
 * @ClassName ClassController
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 11/04/2019 16:17
 * @Version 1.0
 */
@Controller
@RequestMapping("admin/")
public class ClassController {
    private ClassService classService;

    @Autowired
    private void classService(ClassService classService) {
        this.classService = classService;
    }

    /**
     * @param
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 获取班级列表
     * @Date 11/04/2019 17:06
     */
    @ResponseBody
    @RequestMapping(value = "getClassList", method = RequestMethod.POST)
    BaseResult getClassList() {
        return classService.getClassList();
    }

    /**
     * @param cNo
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 获取单个班级信息
     * @Date 11/04/2019 17:09
     */
    @ResponseBody
    @RequestMapping(value = "getClass", method = RequestMethod.POST)
    BaseResult getClass(String cNo) {
        return classService.getClass(cNo);
    }

    /**
     * @param cNo
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 删除班级
     * @Date 11/04/2019 17:09
     */
    @ResponseBody
    @RequestMapping(value = "removeClass", method = RequestMethod.POST)
    BaseResult removeClass(String cNo) {
        return classService.removeClass(cNo);
    }

    /**
     * @param sClass
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 更新班级信息
     * @Date 11/04/2019 17:09
     */
    @ResponseBody
    @RequestMapping(value = "updateClass", method = RequestMethod.POST)
    BaseResult updateClass(SClass sClass) {
        return classService.updateClassInfo(sClass);
    }

    /**
     * @param sClass
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 添加班级
     * @Date 11/04/2019 17:09
     */
    @ResponseBody
    @RequestMapping(value = "addClass", method = RequestMethod.POST)
    BaseResult addClass(SClass sClass) {
        return classService.addClass(sClass);
    }
}
