package top.flytop.studentsign.controller.sysadmin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.dto.DataTablePageUtil;
import top.flytop.studentsign.pojo.Notice;
import top.flytop.studentsign.service.NoticeService;
import top.flytop.studentsign.utils.FileUtil;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
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
@RequestMapping("sysadmin/")
public class NoticeController {
    private NoticeService noticeService;

    @Autowired
    private void noticeService(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    /**
     * @param noticeImg
     * @param request
     * @return com.alibaba.fastjson.JSONObject
     * @Description TODO
     * @date 2019/1/30 18:12
     */
    @RequestMapping("noticeImageUpload")
    @ResponseBody
    public JSONObject noticeImageUpload(MultipartFile noticeImg, HttpServletRequest request) {
        String realName = "";
        String dir = "/noticeImg/";
        if (noticeImg != null) {
            BaseResult saveResult = FileUtil.saveFileByReq(request, null, "noticeImg", dir);
            String filePath = String.valueOf(saveResult.getData());
            String result = "{\"errno\":0, \"data\":[\"" + filePath + "\"]}";
            System.out.println(result);
            //将路径中的\换成/，否则json转换会报错
            String a = result.replace('\\', '/');
            return (JSONObject) JSON.parse(a);
        }
        return null;
    }

    /**
     * @param notice
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/1/31 10:52
     */
    @RequestMapping(value = "addNotice", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult addNotice(Notice notice) {
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        notice.setCreateTime(createTime);
        System.out.println(notice);
        return noticeService.saveNotice(notice);
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 获取所有通知或以关键词搜索
     * @Date 2019/1/31 13:39
     */
    @RequestMapping(value = "getAllNotice", method = RequestMethod.POST)
    @ResponseBody
    public DataTablePageUtil getAllNotice(HttpServletRequest request) {
        DataTablePageUtil<Notice> dataTable = new DataTablePageUtil<>(request);

        //每页显示5条数据
        dataTable.setLength(5);
        Map<String, Object> paraMap = new HashMap<>();
        //加入搜索关键词
        paraMap.put("searchWord", dataTable.getSearch());

        PageHelper.startPage(dataTable.getPage_num(), dataTable.getPage_size());
        List<Notice> result = noticeService.getNotice(paraMap);
        PageInfo<Notice> pageInfo = new PageInfo<>(result);

        //封装数据给DataTables
        dataTable.setData(pageInfo.getList());
        dataTable.setRecordsTotal((int) pageInfo.getTotal());
        dataTable.setRecordsFiltered(dataTable.getRecordsTotal());

        return dataTable;
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

    /**
     * @param notice
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/1/31 20:24
     */
    @RequestMapping("updateNotice")
    @ResponseBody
    public BaseResult updateNotice(Notice notice) {
        return noticeService.updateNotice(notice);
    }

    /**
     * @param noticeId
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/2/1 13:23
     */
    @RequestMapping("removeNotice")
    @ResponseBody
    public BaseResult removeNotice(Integer noticeId) {
        return noticeService.removeNotice(noticeId);
    }

    /**
     * @param noticeId
     * @param status
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/2/1 15:29
     */
    @RequestMapping("changeNoticeStatus")
    @ResponseBody
    public BaseResult changeNoticeStatus(Integer noticeId, Integer status) {
        return noticeService.changeNoticeStatus(noticeId, status);
    }

    /**
     * @param pageNo
     * @param pageSize
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/2/3 18:27
     */
    @RequestMapping(value = "getNoticeForS", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getNoticeForS(Integer pageNo, Integer pageSize) {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("status1", 1);
        paraMap.put("status2", 2);
        PageHelper.startPage(pageNo, pageSize);
        List<Notice> list = noticeService.getNotice(paraMap);
        PageInfo pageInfo = new PageInfo<>(list);
        return new BaseResult<>(true, pageInfo);
    }

    public Notice getPrevNotice(Integer noticeId) {
        return null;
    }


}
