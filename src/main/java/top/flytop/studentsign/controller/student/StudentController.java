package top.flytop.studentsign.controller.student;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.pojo.Student;
import top.flytop.studentsign.service.FaceService;
import top.flytop.studentsign.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("student/")
//与initBinder配合使用
@ControllerAdvice("top.flytop.studentsign.controller")
public class StudentController {
    @Autowired
    private UserService userService;
    @Autowired
    private FaceService faceService;
    private Logger logger;

    /**
     * @param binder
     * @return void
     * @Description TODO 解决date类型为空串时，无法转换为实体类
     * @date 2019/1/16 19:56
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    /**
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 根据学号获取学生信息
     * @date 2019/1/8 17:39
     */
    @RequestMapping(value = "getStudentInfo", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult studentInfoGet(HttpServletRequest request) {
        String sNo = request.getSession().getAttribute("currentUser").toString();
        Student student = userService.getStuInfo(sNo);
        if (student != null) {
            return new BaseResult<>(true, student);
        } else {
            return new BaseResult(false, 1, "查无此人！");
        }
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 学生修改密码的方法
     * @Date 2019/2/19 17:20
     */
    @RequestMapping(value = "changePwd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult changePwd(HttpServletRequest request) {
        return userService.changePwd(request);
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 获取当前用户的图像
     * @Date 2019/3/17 22:03
     */
    @RequestMapping(value = "getStuFaceImg", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getStuFaceImg(HttpServletRequest request) {
        String sNo = request.getSession().getAttribute("currentUser").toString();
        BaseResult result = faceService.getUserFaceList(sNo);
        return result;
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 添加人脸数据
     * @Date 2019/3/13 22:29
     */
    @RequestMapping(value = "addFaceImg", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult addFaceImg(HttpServletRequest request) {
        try {
            BaseResult addRes = faceService.addFace(request);
            return addRes;
        } catch (IOException e) {
            e.printStackTrace();
            return BaseResult.fail(1, "添加失败，请稍后再试！");
        }
    }

}
