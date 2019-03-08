package top.flytop.studentsign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.pojo.Student;
import top.flytop.studentsign.service.FaceService;
import top.flytop.studentsign.utils.FaceUtil;

import java.util.ArrayList;

/**
 * @ClassName FaceServiceImpl
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/3/8 13:39
 * @Version 1.0
 */
@Service
public class FaceServiceImpl implements FaceService {
    private FaceUtil faceUtil;

    @Autowired
    private void FaceUtil(FaceUtil faceUtil) {
        this.faceUtil = faceUtil;
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

    /**
     * @param uid
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 获取人脸列表
     * @Date 2019/3/7 22:42
     */
    @Override
    public BaseResult getUserFaceList(String uid) {
        BaseResult faceResult = faceUtil.getUserFace(uid);
        if (!faceResult.isSuccess())
            return faceResult;
        ArrayList faceList = (ArrayList) faceResult.getData();
        return faceList.size() == 0 ?
                BaseResult.success(null) : faceResult;
    }

    /**
     * @param stu
     * @param image
     * @return top.flytop.studentsign.dto.BaseResult<java.lang.String>
     * @Description TODO 云端人脸库添加人脸信息
     * @date 2019/1/5 18:59
     */
    @Override
    public BaseResult addFace(Student stu, String image) {
        if (image == null) {
            return BaseResult.fail(1, "没有图像，请重试");
        }
        // 人脸库注册
        BaseResult regResult = faceUtil.faceReg(stu.getsNo(), image);
        if (regResult.isSuccess()) {
            // 注册成功
            return BaseResult.success(null);
        }
        // 人脸库注册失败
        return BaseResult.fail(1, regResult.getErrMsg());
    }

    /**
     * @param uid
     * @param faceToken
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 人脸删除方法
     * @Date 2019/3/8 19:26
     */
    public BaseResult removeFace(String uid, String faceToken) {
        BaseResult result = faceUtil.faceRemove(uid, faceToken);
        System.out.println("result:" + result);
        return result;
    }

}
