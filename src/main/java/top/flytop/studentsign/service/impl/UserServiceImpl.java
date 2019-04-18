package top.flytop.studentsign.service.impl;

import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.exception.BusinessException;
import top.flytop.studentsign.mapper.StudentMapper;
import top.flytop.studentsign.mapper.UserMapper;
import top.flytop.studentsign.pojo.Student;
import top.flytop.studentsign.pojo.User;
import top.flytop.studentsign.service.UserService;
import top.flytop.studentsign.utils.ExcelUtil;
import top.flytop.studentsign.utils.ShiroMd5;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private static final String defaultPwd = "123456";
    private UserMapper userMapper;
    private StudentMapper studentMapper;

    @Autowired
    private void userMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    private void studentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    /**
     * @param sNo
     * @return top.flytop.studentsign.pojo.Student
     * @Description TODO 获取学生基本信息
     * @date 2019/1/20 16:44
     */
    @Override
    public Student getStuInfo(String sNo) {
        Student s = studentMapper.getStuGeneralInfo(sNo);
        System.out.println(s);
        return s;
    }

    /**
     * @param
     * @return java.util.List<top.flytop.studentsign.pojo.Student>
     * @Description TODO 获取所有学生
     * @date 2019/1/20 16:44
     */
    @Override
    public List<Student> getAllStudent() {
        return studentMapper.getAllStudent();
    }

    /**
     * @param stu
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 本地保存学生数据
     * @date 2019/1/5 18:58
     */
    @Override
    public BaseResult addStudent(Student stu) {
        try {
            //添加学生
            studentMapper.addStudent(stu);
            //添加用户
            userMapper.addUser(buildUser(stu.getsNo()));
        } catch (Exception e) {
            throw new RuntimeException();
        }
        // 数据库保存成功
        return BaseResult.success("注册成功！");
    }

    /**
     * @param username
     * @return top.flytop.studentsign.pojo.User
     * @Description TODO 根据传入的用户名，构建默认密码学生用户
     * @Date 18/04/2019 14:19
     */
    private User buildUser(String username) {
        /*添加用户*/
        User user = new User();
        user.setUsername(username);
        user.setSalt(String.valueOf(System.currentTimeMillis()));
        String pwdEncrypt = ShiroMd5.md5Encrypt(defaultPwd, user.getSalt());
        user.setPwd(pwdEncrypt);
        user.setType(0);
        return user;
    }

    /*    *//**
     * @param faceImage
     * @param id
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/20 16:36
     *//*
    @Override
    public BaseResult loginByFace(String faceImage, String id) {
        BaseResult<Map> searResult = faceUtil.faceSearch(faceImage, id);
        if (!searResult.isSuccess()) {
            //异常，直接返回至Controller
            return searResult;
        }
        Map data = searResult.getData();
        if (data.get("score") != null && (Double) data.get("score") >= 80) {
            return new BaseResult<>(true, id + " 登录成功！");
        } else {
            return new BaseResult(false, 1, "未匹配到您的信息，请重试!");
        }
        return null;
    }*/

    /**
     * @param student
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 修改学生信息
     * @date 2019/1/20 16:45
     */
    @Override
    public BaseResult updateInfo(Student student) {
        if (studentMapper.updateStuInfo(student))
            return new BaseResult<>(true, "修改成功！");
        else
            return new BaseResult(false, 1, "修改失败！请重试");
    }

    /**
     * @param sNos
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 删除学生（及用户、签到记录、请假记录）
     * @date 2019/1/20 16:45
     */
    @Override
    public BaseResult batchRemoveUser(String[] sNos) {
        System.out.println("大小：" + sNos.length + "  " + Arrays.toString(sNos));
        /*签到、请假记录在数据库外键已经设置
        ON DELETE CASCADE，无需编码删除*/
        int stuCount = 0;
        int userCount = 0;
        try {
            stuCount = studentMapper.deleteStudent(sNos);
            userCount = userMapper.deleteUser(sNos, 1);
        } catch (Exception e) {
            throw new RuntimeException("删除失败");
        }
        return BaseResult.success("删除成功! <br/>其中学生数：" + stuCount + ", 账号数：" + userCount);
    }


    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 修改密码
     * @Date 2019/2/19 17:23
     */
    @Override
    public BaseResult changePwd(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd_1");
        String username = String.valueOf(session.getAttribute("currentUser"));
        User user = userMapper.getUser(username);
        String oldPwdEncrypt = ShiroMd5.md5Encrypt(oldPwd, user.getSalt());
        //原密码输入不正确
        if (!user.getPwd().equals(oldPwdEncrypt)) {
            return BaseResult.fail(1, "身份验证失败，旧密码不正确");
        }
        /*对新密码加密*/
        String newPwdEncrypt = ShiroMd5.md5Encrypt(newPwd, user.getSalt());
        return userMapper.changePwdByUsername(newPwdEncrypt, username) ? BaseResult.success("修改成功，请牢记")
                : BaseResult.fail(1, "修改失败，请稍后再试！");

    }

    /**
     * @param in         输入流
     * @param uploadFile Excel文件
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 批量导入学生信息
     * @Date 2019/2/19 17:21
     */
    @Override
    public BaseResult batchImportStu(InputStream in, MultipartFile uploadFile) throws Exception {
        String filename = uploadFile.getOriginalFilename();
        List<Student> students = new ArrayList<Student>();
        List<List<Object>> list = ExcelUtil.getListByExcel(in, filename);
        System.out.println(list);
        String gender = null;
        String sName = null;
        String cNo = null;
        Date birthday = null;
        String buildingNo = null;
        String roomNo = null;
        String phoneNum = null;
        int rowNo = 0;
        for (List row : list) {
            ++rowNo;
            String sNo = String.valueOf(row.get(0));
            if (StringUtils.isBlank(sNo)) {
                throw new BusinessException(1, "学号不能为空，错误行号：" + rowNo + "<Br>" + row);
            }
            sName = String.valueOf(row.get(1));
            if (StringUtils.isNotBlank(String.valueOf(row.get(2))))
                cNo = String.valueOf(row.get(2));
            gender = String.valueOf(row.get(3));
            if (StringUtils.isNotBlank(String.valueOf(row.get(4))))
                birthday = Date.valueOf(String.valueOf(row.get(4)));
            buildingNo = String.valueOf(row.get(5));
            roomNo = String.valueOf(row.get(6));
            phoneNum = String.valueOf(row.get(7));

            Student student = new Student(sNo, sName, cNo, gender, birthday,
                    buildingNo, roomNo, phoneNum);
            try {
                students.add(student);
                //添加用户
                userMapper.addUser(buildUser(student.getsNo()));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("导入失败");
            }
            log.debug("导入的列表：{}", students);
        }
        int count = studentMapper.batchAddStudent(students);
        return BaseResult.success("执行成功，共导入 " + count + " 条数据");
    }

    /**
     * @param
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 批量学生密码重置
     * @Date 2019/2/19 21:20
     */
    @Override
    public BaseResult batchResetUserPwd(String[] username) {
        int count = 0;

        if (username.length > 1) {
            /*批量处理*/
            List<User> list = new ArrayList<>();
            ;
            for (String name : username) {
                list.add(buildUser(name));
            }
            count = userMapper.resetUserPwd(list);
        } else {
            /*单个*/
            count = userMapper.resetUserPwd(Arrays.asList(buildUser(username[0])));
        }

        return BaseResult.success("操作完成，共重置 " + count + " 名用户密码");
    }

    /**
     * @param
     * @return java.util.List<top.flytop.studentsign.pojo.Student>
     * @Description TODO 搜索学生
     * @Date 18/04/2019 21:00
     */
    @Override
    public List<Student> searchStudent(String keyword) {
        return studentMapper.searchStudent(keyword);
    }


}