package top.flytop.studentsign.service;

import org.springframework.web.multipart.MultipartFile;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.pojo.SClass;
import top.flytop.studentsign.pojo.Student;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;

public interface UserService {
    Student getStuInfo(String sNo);

    List<Student> getAllStudent();

    List<SClass> getAllSClass();

    BaseResult addStudent(Student stu);

    BaseResult updateInfo(Student student);

    BaseResult removeUser(String sNo) throws Exception;

    BaseResult changePwd(HttpServletRequest request);

    BaseResult importStuInfo(InputStream in, MultipartFile uploadFile) throws Exception;

    BaseResult initAllUsers() throws Exception;

    BaseResult initStuAccount(String[] sNos) throws Exception;

}
