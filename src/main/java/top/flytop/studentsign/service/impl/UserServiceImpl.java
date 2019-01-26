package top.flytop.studentsign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.mapper.UserMapper;
import top.flytop.studentsign.pojo.Student;
import top.flytop.studentsign.pojo.User;
import top.flytop.studentsign.service.UserService;
import top.flytop.studentsign.utils.FaceUtil;
import top.flytop.studentsign.utils.ImageUtil;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private FaceUtil faceUtil;
    private ImageUtil imageUtil;

    @Autowired
    private void UserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    private void FaceUtil(FaceUtil faceUtil) {
        this.faceUtil = faceUtil;
    }

    @Autowired
    private void ImageUtil(ImageUtil imageUtil) {
        this.imageUtil = imageUtil;
    }

    /**
     * @param sNo
     * @return top.flytop.studentsign.pojo.Student
     * @Description TODO
     * @date 2019/1/20 16:44
     */
    @Override
    public Student getStuInfo(String sNo) {
        Student s = userMapper.getStuGeneralInfo(sNo);
        return s;
    }

    /**
     * @param
     * @return java.util.List<top.flytop.studentsign.pojo.Student>
     * @Description TODO
     * @date 2019/1/20 16:44
     */
    @Override
    public List<Student> getAllStudent() {
        return userMapper.getAllStudent();
    }

    /**
     * @param stu
     * @param image
     * @return top.flytop.studentsign.dto.BaseResult<java.lang.String>
     * @Description TODO 云端人脸库添加人脸信息
     * @date 2019/1/5 18:59
     */
    @Override
    public BaseResult<String> addFace(Student stu, String image) {
        if (image == null) {
            return new BaseResult<>(false, 1, "没有图像，请重试");
        }
        // 人脸库注册
        BaseResult regResult = faceUtil.faceReg(stu.getsNo(), image);
        if (regResult.isSuccess()) {
            // 注册成功
            return new BaseResult<>(true, null);
        }
        // 人脸库注册失败
        return new BaseResult<>(false, 1, regResult.getErrMsg());
    }

    /**
     * @param stu
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 本地保存学生数据
     * @date 2019/1/5 18:58
     */
    @Override
    public BaseResult addStudent(Student stu) {
        if (userMapper.addStudent(stu))
            // 数据库保存成功
            return new BaseResult<>(true, "注册成功！");
        else
            return new BaseResult<>(true, "注册失败！");
    }

    /**
     * @param faceImage
     * @param id
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/20 16:36
     */
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
    }

    /**
     * @param username
     * @param pwd
     * @return top.flytop.studentsign.dto.BaseResult<java.lang.Integer>
     * @Description TODO
     * @date 2019/1/20 16:37
     */
    @Override
    public BaseResult<Integer> loginByPwd(String username, String pwd) {
        User user = userMapper.userLogin(username, pwd);
        if (user != null)
            return new BaseResult<>(true, user.getTypeId());
        else
            return new BaseResult<>(false, 1, "用户名或密码错误，请重试！");

    }

    /**
     * @param student
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/20 16:45
     */
    @Override
    public BaseResult updateInfo(Student student) {
        if (userMapper.updateStuInfo(student))
            return new BaseResult<>(true, "修改成功！");
        else
            return new BaseResult(false, 1, "修改失败！请重试");
    }

    /**
     * @param sNo
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/20 16:45
     */
    @Override
    @Transactional
    public BaseResult removeUser(String sNo) throws Exception {
        System.out.println(userMapper.getUser(sNo));
        if (userMapper.getUser(sNo) != null) {
            //user表中存在该条记录
            if (userMapper.deleteStudent(sNo) == 1 && userMapper.deleteUser(sNo) == 1)
                return new BaseResult<>(true, "删除成功");
            else
                throw new RuntimeException("删除失败，请重试！");
        } else if (userMapper.deleteStudent(sNo) == 1) {
            return new BaseResult<>(true, "删除成功");
        } else {
            throw new RuntimeException("删除失败，请重试！");
        }
    }

    /**
     * @param image
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/20 16:45
     */
    @Override
    public BaseResult faceChecker(String image) {
        return faceUtil.faceChecker(image);
    }

}