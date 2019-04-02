package top.flytop.studentsign.controller.student;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.dto.DataTablePageUtil;
import top.flytop.studentsign.pojo.SignIn;
import top.flytop.studentsign.service.SignInService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName signInController
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/12 17:18
 * @Version 1.0
 */
@Controller
@RequestMapping("student/")
public class StuSignInController {
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
    public DataTablePageUtil signList(HttpServletRequest request) {
        String sno = String.valueOf(request.getSession().getAttribute("currentUser"));
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        System.out.println("startTime:" + startTime);
        DataTablePageUtil dataTable = new DataTablePageUtil(request);
        //每页显示10条数据
        dataTable.setLength(10);
        PageHelper.startPage(dataTable.getPage_num(), dataTable.getPage_size());
        List<SignIn> result = signInService.getPersonalSignRecord(sno, startTime, endTime);
        PageInfo<SignIn> pageInfo = new PageInfo<>(result);
        //封装数据给DataTables
        dataTable.setData(pageInfo.getList());
        dataTable.setRecordsTotal((int) pageInfo.getTotal());
        dataTable.setRecordsFiltered(dataTable.getRecordsTotal());
        return dataTable;
    }

    /**
     * @param faceImage
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 学生签到方法
     * @date 2018/12/29 22:10
     */
    @RequestMapping(value = "stuSign", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult stuSign(String faceImage, HttpServletRequest request) {
        //判断是否可签到
        boolean whetherSign = (boolean) request.getSession().getServletContext().getAttribute("whetherSign");

        return whetherSign ?
                signInService.saveStudentSign(faceImage)
                : BaseResult.fail(1, "签到失败，非签到时间！");
    }
}
