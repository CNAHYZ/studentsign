package top.flytop.studentsign.controller.student;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.pojo.Student;
import top.flytop.studentsign.service.UserService;
import top.flytop.studentsign.utils.FileUtil;
import top.flytop.studentsign.utils.ImageUtil;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("student/")
//与initBinder配合使用
@ControllerAdvice("top.flytop.studentsign.controller")
public class StudentController {
    @Autowired
    private UserService userService;
    @Autowired
    private ImageUtil imageUtil;
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
     * @Description TODO 人脸检测方法，检测成功进行临时存储
     * @date 2019/1/8 17:41
     */
    @RequestMapping(value = "faceChecker", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult faceChecker(HttpServletRequest request) {
        BaseResult<String> saveResult = FileUtil.saveFileByReq(request, null, "file", "temp/");
        System.out.println(saveResult);
        if (saveResult.isSuccess()) {
            //保存成功
            String image = imageUtil.imageToBase64(saveResult.getData());
            BaseResult<String> checkResult = userService.faceChecker(image);
            if (checkResult.isSuccess()) {
                //人脸检测成功
                //返回保存路径
                Map<String, String> map = new HashMap<>();
                map.put("fileSavePath", saveResult.getData());
                map.put("faceToken", checkResult.getData());
                return new BaseResult<Map>(true, map);
            } else return checkResult;
        }
        return saveResult;
    }
}
