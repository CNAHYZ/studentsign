package top.flytop.studentsign.service;

import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.pojo.Student;

/**
 * @ClassName FaceService
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/3/8 13:39
 * @Version 1.0
 */
public interface FaceService {
    BaseResult addFace(Student stu, String image);

    BaseResult faceChecker(String image);

    BaseResult getUserFaceList(String uid);

}
