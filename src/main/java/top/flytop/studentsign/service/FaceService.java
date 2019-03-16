package top.flytop.studentsign.service;

import top.flytop.studentsign.dto.BaseResult;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName FaceService
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/3/8 13:39
 * @Version 1.0
 */
public interface FaceService {
    BaseResult addFace(HttpServletRequest request) throws IOException;

    BaseResult faceChecker(String image);

    BaseResult getUserFaceList(String uid);

    BaseResult removeFaceImg(HttpServletRequest request);
}
