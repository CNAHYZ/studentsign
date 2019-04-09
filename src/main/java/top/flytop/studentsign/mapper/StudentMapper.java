package top.flytop.studentsign.mapper;

import top.flytop.studentsign.pojo.SClass;
import top.flytop.studentsign.pojo.Student;

import java.util.List;

/**
 * @ClassName StudentMapper
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 07/04/2019 22:36
 * @Version 1.0
 */
public interface StudentMapper {
    Student getStuGeneralInfo(String id);

    List<Student> getAllStudent();

    List<SClass> getAllSClass();

    int getTotalCount();

    Boolean updateStuInfo(Student student);

    Boolean addStudent(Student student);

    Integer batchAddStudent(List<Student> students);

    Integer deleteStudent(String[] sNos);

}
