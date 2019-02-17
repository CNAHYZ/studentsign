package top.flytop.studentsign.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
@RequestMapping("admin/")
public class SignInController {
    @Autowired
    private SignInService signInService;

    /**
     * @param dayNum
     * @param request
     * @return top.flytop.studentsign.dto.DataTablePageUtil
     * @Description TODO
     * @Date 2019/2/17 22:49
     */
    @RequestMapping(value = "signList", method = RequestMethod.POST)
    @ResponseBody
    public DataTablePageUtil signList(String dayNum, HttpServletRequest request) {
        DataTablePageUtil dataTable = new DataTablePageUtil(request);
        //每页显示10条数据
        dataTable.setLength(10);
        PageHelper.startPage(dataTable.getPage_num(), dataTable.getPage_size());
        List<SignIn> result = signInService.getSignRecord(dayNum);
        PageInfo<SignIn> pageInfo = new PageInfo<>(result);
        //封装数据给DataTables
        dataTable.setData(pageInfo.getList());
        dataTable.setRecordsTotal((int) pageInfo.getTotal());
        dataTable.setRecordsFiltered(dataTable.getRecordsTotal());
        return dataTable;
    }

    /**
     * @param startTime
     * @param endTime
     * @return top.flytop.studentsign.dto.DataTablePageUtil
     * @Description TODO 自定义日期过滤操作
     * @date 2019/1/26 16:59
     */
    @RequestMapping(value = "signListFilter", method = RequestMethod.POST)
    @ResponseBody
    public DataTablePageUtil signListFilter(String startTime, String endTime, HttpServletRequest request) {
        DataTablePageUtil dataTable = new DataTablePageUtil(request);
        //每页显示10条数据
        dataTable.setLength(10);
        PageHelper.startPage(dataTable.getPage_num(), dataTable.getPage_size());
        List<SignIn> result = signInService.getSignRecordFilter(startTime, endTime);
        PageInfo<SignIn> pageInfo = new PageInfo<>(result);
        //封装数据给DataTables
        dataTable.setData(pageInfo.getList());
        dataTable.setRecordsTotal((int) pageInfo.getTotal());
        dataTable.setRecordsFiltered(dataTable.getRecordsTotal());
        return dataTable;
    }

    /**
     * @param keyword
     * @return top.flytop.studentsign.dto.DataTablePageUtil
     * @Description TODO 签到列表搜索操作
     * @date 2019/1/26 17:01
     */
    @RequestMapping("searchSignList.do")
    @ResponseBody
    public DataTablePageUtil searchSignList(String keyword, HttpServletRequest request) {
        System.out.println(keyword);
        DataTablePageUtil dataTable = new DataTablePageUtil(request);
        //每页显示10条数据
        dataTable.setLength(10);
        PageHelper.startPage(dataTable.getPage_num(), dataTable.getPage_size());
        List<SignIn> result = signInService.getSignRecordByKeyword(keyword);
        PageInfo<SignIn> pageInfo = new PageInfo<>(result);
        //封装数据给DataTables
        dataTable.setData(pageInfo.getList());
        dataTable.setRecordsTotal((int) pageInfo.getTotal());
        dataTable.setRecordsFiltered(dataTable.getRecordsTotal());
        return dataTable;
    }

}
