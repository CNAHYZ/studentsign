
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.mapper.UserMapper;
import top.flytop.studentsign.pojo.Student;
import top.flytop.studentsign.service.impl.UserServiceImpl;
import top.flytop.studentsign.utils.ImageUtil;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class UserTest {
    @Resource
    public UserServiceImpl userServiceImpl;
    @Autowired
    public UserMapper userMapper;
    @Resource
    public Student stu;
    @Autowired
    private ImageUtil imageUtil;

    @Test
    public void testGetStuInfo() {
        Student s = userServiceImpl.getStuInfo("1");
        System.out.println(s);
    }

    @Test
    public void testGetAllStudent() {
        System.out.println(userMapper.getAllStudent());
        ;
    }


    @Test
    public void testAddStuInfo() {
        stu.setsNo("23");
        stu.setsName("yyyy");
        stu.setBuildingNo("2");
        System.out.println(userMapper.addStudent(stu));
    }

    @Test
    public void testStudentReg() {
        stu.setsNo("27");
        stu.setsName("yyyy");
        String im = imageUtil.imageToBase64("D://2.jpg");
        BaseResult res = userServiceImpl.addFace(stu, im);
        System.out.println(res);
    }

    @Test
    public void testUserLogin() {
        String im = imageUtil.imageToBase64("D://1.jpg");
        System.out.println(userServiceImpl.loginByFace(im, "23"));
    }

 /*   @Test
    public void testLoginByPwd() {
        BaseResult s = userServiceImpl.loginByPwd("admin", "123");
        System.out.println(s);
    }*/

    @Test
    public void testRemoveUser() throws Exception {
        System.out.println(userServiceImpl.removeUser("25"));
    }

    @Test
    public void testGetUser() {
        System.out.println(userMapper.getUser("23"));
    }

    @Test
    public void testDelStudent() {
        System.out.println(userMapper.deleteStudent("23"));
    }


}
