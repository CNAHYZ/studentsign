package top.flytop.studentsign.controller.admin;

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
@RequestMapping("admin/")
public class SignInController {
    @Autowired
    private SignInService signInService;

    /**
     * @param dayNum
     * @param request
     * @return top.flytop.studentsign.dto.DataTablePageUtil
     * @Description TODO 获取签到列表
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

        return signInDataTable(dataTable, result);
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

        return signInDataTable(dataTable, result);
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

        return signInDataTable(dataTable, result);
    }

    /**
     * @param sNo
     * @param request
     * @return top.flytop.studentsign.dto.DataTablePageUtil
     * @Description TODO 获取某个学生的所有签到记录
     * @Date 20/04/2019 18:10
     */
    @RequestMapping(value = "getSignBySNo", method = RequestMethod.POST)
    @ResponseBody
    public DataTablePageUtil getSignBySNo(String sNo, HttpServletRequest request) {
        DataTablePageUtil dataTable = new DataTablePageUtil(request);
        //每页显示10条数据
        dataTable.setLength(10);
        PageHelper.startPage(dataTable.getPage_num(), dataTable.getPage_size());
        List<SignIn> result = signInService.getPersonalSignRecord(sNo, null, null);

        return signInDataTable(dataTable, result);
    }

    /**
     * @param dayNum 获取签到数目
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 20/04/2019 18:07
     */
    @RequestMapping("getSignCount.do")
    @ResponseBody
    public BaseResult getSignCount(int dayNum) {
        return BaseResult.success(signInService.getSignCount(dayNum));
    }

    /**
     * @param dataTable
     * @param result
     * @return top.flytop.studentsign.dto.DataTablePageUtil
     * @Description TODO dataTable通用封装
     * @Date 20/04/2019 18:09
     */
    private DataTablePageUtil signInDataTable(DataTablePageUtil dataTable, List<SignIn> result) {
        PageInfo<SignIn> pageInfo = new PageInfo<>(result);
        //封装数据给DataTables
        dataTable.setData(pageInfo.getList());
        dataTable.setRecordsTotal((int) pageInfo.getTotal());
        dataTable.setRecordsFiltered(dataTable.getRecordsTotal());
        return dataTable;
    }

}
