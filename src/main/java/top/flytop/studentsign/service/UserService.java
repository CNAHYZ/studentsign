package top.flytop.studentsign.service;

import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.pojo.Student;

import java.util.List;

public interface UserService {
    Student getStuInfo(String sNo);

    List<Student> getAllStudent();

    BaseResult addFace(Student stu, String image);

    BaseResult addStudent(Student stu);

    BaseResult loginByFace(String faceImage, String id);

    BaseResult<Integer> loginByPwd(String username, String pwd);

    BaseResult updateInfo(Student student);

    BaseResult removeUser(String sNo) throws Exception;

    BaseResult faceChecker(String image);
}
