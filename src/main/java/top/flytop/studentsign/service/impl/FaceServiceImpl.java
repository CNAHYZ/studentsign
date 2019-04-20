package top.flytop.studentsign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Encoder;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.service.FaceService;
import top.flytop.studentsign.utils.FaceUtil;
import top.flytop.studentsign.utils.FileUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName FaceServiceImpl
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/3/8 13:39
 * @Version 1.0
 */
@Service
public class FaceServiceImpl implements FaceService {
    private static final int faceLimit = 3;
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
        ArrayList<Map<String, String>> list = (ArrayList) faceResult.getData();
        if (list == null || list.size() == 0)
            return faceResult;
        ArrayList<Map> newList = new ArrayList<>();
        for (Map<String, String> item : list) {
            String faceToken = (String) item.get("faceToken");
            String url = "/userUpload/faceImg/" + uid + "/" + faceToken + ".png";
            item.put("url", url);
            newList.add(item);
        }
        return BaseResult.success(newList);
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 添加人脸数据
     * @date 2019/1/5 18:59
     */
    @Override
    public BaseResult addFace(HttpServletRequest request) throws IOException {
        /*获取sno*/
        String sNo = request.getParameter("sNo");
        if (sNo == null) {
            sNo = request.getSession().getAttribute("currentUser").toString();
        }

        int currentFaceCount = 0;
        BaseResult checkResult = null;
        BaseResult searchResult = null;
        String faceToken = null;
        /*判断当前人脸数是否达到上限*/
        if (getUserFaceList(sNo).isSuccess()) {
            /*等待返回信息*/
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentFaceCount = ((ArrayList) getUserFaceList(sNo).getData()).size();
            if (currentFaceCount == faceLimit)
                return BaseResult.fail(1, "人脸数已达上限，无需继续添加！");
        }
        /*从request中获取faceImage*/
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile faceImage = multipartRequest.getFile("faceImage");
        if (faceImage == null) {
            return BaseResult.fail(1, "没有图像，请重试");
        }

        String faceToBase64 = new BASE64Encoder().encode(faceImage.getBytes());

        if (currentFaceCount == 0) {
            /*如果是第一张人脸图片，则进行人脸检测*/
            checkResult = faceChecker(faceToBase64);
            System.out.println("=====checkResult:" + checkResult);
            /*人脸检测成功*/
            if (checkResult.isSuccess()) {
                //返回faceToken
                faceToken = String.valueOf(checkResult.getData());
            } else {
                return checkResult;
            }
        } else {
            /*如果不是第一张人脸图片，则进行人脸对比，检测是否是同一人*/
            searchResult = faceUtil.faceSearch(faceToBase64, sNo);
            System.out.println("=====searchResult:" + searchResult);
            /*人脸检测成功*/
            if (searchResult.isSuccess()) {
                faceToken = String.valueOf(((HashMap) searchResult.getData()).get("faceToken"));
                double score = Double.valueOf(String.valueOf(((HashMap) searchResult.getData()).get("score")));
                if (score < 75)
                    return BaseResult.fail(1, "该图片与之前上传的图片可能不是同一人，请重新上传！");
            } else
                return BaseResult.fail(1, "上传失败，图片中未检测到信息，或人脸不完整、不清晰，请重新上传！");
        }

        // 人脸库注册
        BaseResult regResult = faceUtil.faceReg(sNo, faceToken);
        if (regResult.isSuccess()) {
            /*注册成功*/
            //本地保存图片
            BaseResult saveResult = FileUtil.saveImage(request, String.valueOf(regResult.getData()), sNo);
            System.out.println("=====saveResult:" + saveResult);
            return saveResult.isSuccess() ? BaseResult.success("添加成功！") : saveResult;
        }
        // 人脸库注册失败
        return BaseResult.fail(1, regResult.getErrMsg());
    }


    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 人脸删除方法
     * @Date 2019/3/8 19:26
     */
    public BaseResult removeFaceImg(HttpServletRequest request) {
        String uid = request.getParameter("uid");
        String faceToken = request.getParameter("faceToken");

        /*云端删除*/
        BaseResult result = faceUtil.faceRemove(uid, faceToken);

        /*删除本地文件*/
        String realPath = request.getSession().getServletContext().getRealPath("/userUpload/faceImg/");
        String path = realPath + uid + "/" + faceToken + ".png";
        File localImage = new File(path);
        localImage.delete();

        System.out.println("result:" + result);
        return result;
    }

}
