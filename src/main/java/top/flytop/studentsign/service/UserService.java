package top.flytop.studentsign.service;

import org.springframework.web.multipart.MultipartFile;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.pojo.Student;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;

public interface UserService {
    Student getStuInfo(String sNo);

    List<Student> getAllStudent();

    BaseResult addStudent(Student stu);

    BaseResult updateInfo(Student student);

    BaseResult batchRemoveUser(String[] sNos);

    BaseResult changePwd(HttpServletRequest request);

    BaseResult batchImportStu(InputStream in, MultipartFile uploadFile) throws Exception;

    BaseResult batchResetUserPwd(String[] username);

    List<Student> searchStudent(String keyword);
}
