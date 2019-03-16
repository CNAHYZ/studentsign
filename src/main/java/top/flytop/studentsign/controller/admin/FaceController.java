package top.flytop.studentsign.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.service.FaceService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName FaceController
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/3/8 19:05
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class FaceController {
    private FaceService faceService;

    @Autowired
    private void faceService(FaceService faceService) {
        this.faceService = faceService;
    }

    /**
     * @param sNo
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/3/9 16:28
     */
    @RequestMapping(value = "getStuFaceImg", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getStuFaceImg(String sNo) {
        BaseResult result = faceService.getUserFaceList(sNo);
        return result;
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 添加人脸数据
     * @Date 2019/3/13 22:29
     */
    @RequestMapping(value = "addFaceImg", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult addFaceImg(HttpServletRequest request) {
        try {
            BaseResult addRes = faceService.addFace(request);
            return addRes;
        } catch (IOException e) {
            e.printStackTrace();
            return BaseResult.fail(1, "添加失败，请稍后再试！");
        }
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 删除人脸数据
     * @Date 2019/3/13 22:12
     */
    @RequestMapping(value = "removeFaceImg", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult removeFaceImg(HttpServletRequest request) {
        BaseResult removeRes = faceService.removeFaceImg(request);
        return removeRes;
    }

}
