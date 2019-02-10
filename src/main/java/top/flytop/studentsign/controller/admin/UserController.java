package top.flytop.studentsign.controller.admin;

import com.alibaba.fastjson.JSON;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/")
//与initBinder配合使用
@ControllerAdvice("top.flytop.studentsign.controller")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ImageUtil imageUtil;

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
     * @param
     * @return top.flytop.studentsign.dto.BaseResult<java.util.List>
     * @Description TODO 获取学生列表
     * @date 2019/1/8 17:37
     */
    @RequestMapping(value = "studentList", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<List> studentList() {
        return new BaseResult(true, userService.getAllStudent());
    }

    /**
     * @param request
     * @param student
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 修改学生信息
     * @date 2019/1/8 17:37
     */
    @RequestMapping(value = "studentInfoAlter", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult studentInfoAlter(HttpServletRequest request, Student student) {
        if (!student.getFaceImage().equals("")) {
            //获取绝对路径
            String realPath = request.getSession().getServletContext().getRealPath("stu_pic/");
            // 本地保存面部图像
            String imagePath = (String) new ImageUtil().saveImage(student.getsNo(), student.getFaceImage(), realPath).getData();
            // 将图像路径保存至数据库
            student.setFaceImage(imagePath);
        }
        System.out.println(student);
        return userService.updateInfo(student);
    }

    /**
     * @param sNo
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 根据学号获取学生信息
     * @date 2019/1/8 17:39
     */
    @RequestMapping(value = "studentInfoGet", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult studentInfoGet(String sNo) {
        Student student = userService.getStuInfo(sNo);
        if (student != null) {
            return new BaseResult<>(true, student);
        } else {
            return new BaseResult(false, 1, "查无此人！");
        }
    }

    /**
     * @param sNo
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 根据学号删除学生
     * @date 2019/1/8 17:39
     */
    @RequestMapping(value = "removeUser", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult removeUser(String sNo) {
        try {
            BaseResult delResult = userService.removeUser(sNo);
            if (delResult.isSuccess())
                return delResult;
            else
                return new BaseResult(false, 1, "删除失败");

        } catch (Exception e) {
//            logger.info("=================" + e.getMessage());
            return new BaseResult(false, 1, "删除失败");
        }
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 添加学生
     * @date 2019/1/8 17:40
     */
    @RequestMapping(value = "studentAdd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult studentAdd(HttpServletRequest request) {
        //从前台发送的数据中获取参数
        String stuString = request.getParameter("stu");
        String oldPath = request.getParameter("fileSavePath");
        String faceToken = request.getParameter("faceToken");
        final String dir = "stu_pic/";
        try {
            System.out.println("============" + stuString);
            Student student = JSON.parseObject(stuString, Student.class);
            System.out.println("------------" + student);
            if (oldPath != null && faceToken != null) {
                String fileName = student.getsNo() + oldPath.substring(oldPath.lastIndexOf("."));
                //新路径
                String newPath = request.getServletContext().getRealPath(dir) + fileName;
                System.out.println(newPath);
                //人脸库注册成功
                if (userService.addFace(student, faceToken).isSuccess()) {
                    //将图片从临时文件夹移入人脸文件夹，并以学号命名
                    if (FileUtil.MoveRename(oldPath, newPath, true)) {
                        //本地数据库中添加学生信息
                        student.setFaceImage(dir + fileName);
                    }
                }
            }
            if (userService.addStudent(student).isSuccess())
                return new BaseResult<String>(true, student.getsNo() + " 添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BaseResult<String>(false, 1, "添加失败！");
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
        BaseResult<String> saveResult = imageUtil.saveImgByReq(request, null, "file", "temp/");
        System.out.println(saveResult);
        if (saveResult.isSuccess()) {
            //保存成功
            String image = imageUtil.imageToBase64(saveResult.getData());
            BaseResult<String> checkResult = userService.faceChecker(image);
            System.out.println("=====checkResult:" + checkResult);
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
