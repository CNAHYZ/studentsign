package top.flytop.studentsign.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.flytop.studentsign.pojo.Student;
import top.flytop.studentsign.pojo.User;

import java.util.List;


@Repository
public interface UserMapper {
    Student getStuGeneralInfo(String id);

    List<Student> getAllStudent();

    User getUser(String username);

    User userLogin(@Param("username") String username, @Param("pwd") String pwd);

    Boolean updateStuInfo(Student student);

    Integer deleteStudent(String sNo);

    Integer deleteUser(String username);

    Boolean addStudent(Student student);

    Boolean changePwdBySNo(@Param("pwd") String pwd, @Param("sNo") String sNo);
}
