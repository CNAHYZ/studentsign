package top.flytop.studentsign.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.flytop.studentsign.pojo.SClass;
import top.flytop.studentsign.pojo.Student;
import top.flytop.studentsign.pojo.User;

import java.util.List;


@Repository
public interface UserMapper {
    Student getStuGeneralInfo(String id);

    List<Student> getAllStudent();

    List<SClass> getAllSClass();

    User getUser(String username);

    User userLogin(@Param("username") String username, @Param("pwd") String pwd);

    Boolean updateStuInfo(Student student);

    Integer deleteStudent(String sNo);

    Integer deleteUser(String username);

    Boolean addStudent(Student student);

    Boolean changePwdByUsername(@Param("pwd") String pwd, @Param("username") String username);

    Integer batchAddStudent(List<Student> students);

    Integer initAllUsers(@Param("pwd") String pwd, @Param("salt") String salt);

    Integer initUser(User user);

    Integer resetStuPwd(User user);
}
