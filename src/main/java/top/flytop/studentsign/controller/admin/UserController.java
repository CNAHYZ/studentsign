package top.flytop.studentsign.controller.admin;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.dto.DataTablePageUtil;
import top.flytop.studentsign.exception.BusinessException;
import top.flytop.studentsign.pojo.Student;
import top.flytop.studentsign.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("admin/")
//与initBinder配合使用
@ControllerAdvice("top.flytop.studentsign.controller")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

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
    @RequestMapping(value = "getStudentList", method = RequestMethod.POST)
    @ResponseBody
    public DataTablePageUtil studentList(HttpServletRequest request) {
        DataTablePageUtil<Student> dataTable = new DataTablePageUtil<>(request);

        /*开始分页*/
        PageHelper.startPage(dataTable.getPage_num(), dataTable.getPage_size());

        List<Student> result = null;

        /*判断是否是搜索*/
        result = StringUtils.isBlank(dataTable.getSearch())
                ? userService.getAllStudent()
                : userService.searchStudent(dataTable.getSearch());

        PageInfo<Student> pageInfo = new PageInfo<>(result);

        /*封装数据给DataTables*/
        dataTable.setData(pageInfo.getList());
        dataTable.setRecordsTotal((int) pageInfo.getTotal());
        dataTable.setRecordsFiltered(dataTable.getRecordsTotal());

        return dataTable;
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
     * @param sNos
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 根据学号删除学生
     * @date 2019/1/8 17:39
     */
    @RequestMapping(value = "removeUser", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult removeUser(String[] sNos) {
        try {
            return userService.batchRemoveUser(sNos);
        } catch (Exception e) {
//            logger.info("=================" + e.getMessage());
            return BaseResult.fail(1, "删除失败");
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
        try {
            System.out.println("============" + stuString);
            Student student = JSON.parseObject(stuString, Student.class);
            System.out.println("------------" + student);
            if (userService.addStudent(student).isSuccess())
                return new BaseResult<String>(true, student.getsNo() + " 添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BaseResult<String>(false, 1, "添加失败！");
    }

    /**
     * @param filename
     * @param request
     * @param response
     * @return void
     * @Description TODO 文件下载
     * @Date 2019/2/18 18:15
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)

    public void download(String filename, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        System.out.println(filename);
        //需要下载的文件
        String path = request.getSession().getServletContext().getRealPath("/download") + "\\" + filename;
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
        //转码，免得文件名中文乱码
        filename = new String(filename.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while ((len = bis.read()) != -1) {
            out.write(len);
            out.flush();
        }
        out.close();
    }

    /**
     * @param uploadFile 用户上传的文件
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 批量导入学生信息
     * @Date 2019/2/19 17:27
     */
    @RequestMapping(value = "importStuInfo", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult importStuInfo(MultipartFile uploadFile) {
        InputStream in = null;
        log.info("文件名：{} ", uploadFile.getOriginalFilename());
        try {
            in = uploadFile.getInputStream();
            //数据导入
            BaseResult result = userService.batchImportStu(in, uploadFile);
            in.close();
            return result;
        } catch (BusinessException be) {
//            log.error("上传excel导入数据 发生异常：",e.fillInStackTrace());
            be.printStackTrace();
            return BaseResult.fail(be.getCode(), be.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail(1, "请检查格式是否正确，学号是否有重复");
        }
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 管理员修改密码的方法
     * @Date 2019/2/19 17:20
     */
    @RequestMapping(value = "changePwd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult changePwd(HttpServletRequest request) {
        return userService.changePwd(request);
    }

    /**
     * @param
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 重置学生密码
     * @Date 2019/2/19 22:10
     */
    @RequestMapping(value = "resetUserPwd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult initialAllUsers(String[] sNos) {
        try {
            return userService.batchResetUserPwd(sNos);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail(1, "操作失败");
        }
    }

}
