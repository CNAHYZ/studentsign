package top.flytop.studentsign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.service.SignInService;

/**
 * @ClassName signInController
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/12 17:18
 * @Version 1.0
 */
@Controller
public class SignInController {
    @Autowired
    private SignInService signInService;

    /**
     * @param
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/12 17:19
     */
    @RequestMapping(value = "signList", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult signList(String dayNum) {

        return signInService.getSignRecord(dayNum);
    }

    /**
     * @param startTime
     * @param endTime
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 自定义日期过滤操作
     * @date 2019/1/26 16:59
     */
    @RequestMapping(value = "signListFilter", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult signListFilter(String startTime, String endTime) {
        return signInService.getSignRecordFilter(startTime, endTime);
    }

    /**
     * @param keyword
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 签到列表搜索操作
     * @date 2019/1/26 17:01
     */
    @RequestMapping("searchSignList.do")
    @ResponseBody
    public BaseResult searchSignList(String keyword) {
        return signInService.getSignRecordByKeyword(keyword);
    }

    /**
     * @param faceImage
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 学生签到方法
     * @date 2018/12/29 22:10
     */
    @RequestMapping(value = "stuSign", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult stuSign(String faceImage) {
        BaseResult baseResult = signInService.studentSign(faceImage);
        return baseResult;
    }

}
