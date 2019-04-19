package top.flytop.studentsign.controller.student;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.pojo.Notice;
import top.flytop.studentsign.service.NoticeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName NoticeController
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/30 15:42
 * @Version 1.0
 */
@Controller
@RequestMapping("student/")
public class StuNoticeController {
    private NoticeService noticeService;

    @Autowired
    private void noticeService(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    /**
     * @param pageNo
     * @param pageSize
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/2/3 18:27
     */
    @RequestMapping(value = "getNotice", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getNotice(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<Notice> list = noticeService.getNoticeTitle();
        PageInfo pageInfo = new PageInfo<>(list);
        return new BaseResult<>(true, pageInfo);
    }

    /**
     * @param noticeId
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/2/2 12:41
     */
    @RequestMapping(value = "getNoticeDetail", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getNoticeDetail(Integer noticeId) {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("noticeId", noticeId);
        return new BaseResult<>(true, noticeService.getNotice(paraMap));
    }

    public Notice getPrevNotice(Integer noticeId) {
        return null;
    }


}
