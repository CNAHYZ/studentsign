package top.flytop.studentsign.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.dto.DataTablePageUtil;
import top.flytop.studentsign.pojo.Leave;
import top.flytop.studentsign.service.LeaveService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName LeaveController
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/27 13:50
 * @Version 1.0
 */
@Controller
@RequestMapping("admin/")
public class LeaveController {
    private LeaveService leaveService;

    @Autowired
    private void leaveService(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 获取请假列表/时间筛选
     * @date 2019/1/27 19:52
     */
    @RequestMapping("getLeave")
    @ResponseBody
    public DataTablePageUtil getLeave(HttpServletRequest request) {
        String dayNum = request.getParameter("param");
        DataTablePageUtil dataTable = new DataTablePageUtil(request);
        //每页显示10条数据
        dataTable.setLength(10);
        PageHelper.startPage(dataTable.getPage_num(), dataTable.getPage_size());
        List<Leave> result = leaveService.getLeave(dayNum);

        return leaveDataTable(dataTable, result);
    }

    /**
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 获取今日请假数
     * @date 2019/1/27 19:52
     */
    @RequestMapping("getTodayLeaveCount")
    @ResponseBody
    public List<Leave> getTodayLeaveCount() {
        return leaveService.getLeave("0");
    }

    /**
     * @param auditStatus
     * @param sNo
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 获取某生的所有请假记录
     * @Date 2019/3/20 22:22
     */
    @RequestMapping("getLeaveBySNo")
    @ResponseBody
    public BaseResult getLeaveBySNo(@RequestParam(defaultValue = "") Integer auditStatus, String sNo, HttpServletRequest request) {
        return new BaseResult<>(true, leaveService.getLeaveBySNo(sNo, auditStatus));
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 状态筛选
     * @date 2019/1/27 19:52
     */
    @RequestMapping("leaveStatusFilter")
    @ResponseBody
    public DataTablePageUtil leaveStatusFilter(HttpServletRequest request) {
        String status = request.getParameter("param");
        DataTablePageUtil dataTable = new DataTablePageUtil(request);
        //每页显示10条数据
        dataTable.setLength(10);
        PageHelper.startPage(dataTable.getPage_num(), dataTable.getPage_size());
        List<Leave> result = leaveService.getLeaveByStatus(status);

        return leaveDataTable(dataTable, result);
    }

    /**
     * @param keyword
     * @return top.flytop.studentsign.dto.DataTablePageUtil
     * @Description TODO 签到列表搜索操作
     * @date 2019/1/26 17:01
     */
    @RequestMapping("searchLeaveList.do")
    @ResponseBody
    public DataTablePageUtil searchSignList(String keyword, HttpServletRequest request) {
        System.out.println(keyword);
        DataTablePageUtil dataTable = new DataTablePageUtil(request);
        //每页显示10条数据
        dataTable.setLength(10);
        PageHelper.startPage(dataTable.getPage_num(), dataTable.getPage_size());
        List<Leave> result = leaveService.getLeaveListByKeyword(keyword);

        return leaveDataTable(dataTable, result);
    }

    /**
     * @param startTime
     * @param endTime
     * @param request
     * @return top.flytop.studentsign.dto.DataTablePageUtil
     * @Description TODO 自定义时间搜索操作
     * @date 2019/1/26 17:01
     */
    @RequestMapping("leaveTimeFilter.do")
    @ResponseBody
    public DataTablePageUtil leaveTimeFilter(String startTime, String endTime, HttpServletRequest request) {
        DataTablePageUtil dataTable = new DataTablePageUtil(request);
        //每页显示10条数据
        dataTable.setLength(10);
        PageHelper.startPage(dataTable.getPage_num(), dataTable.getPage_size());
        List<Leave> result = leaveService.getLeaveByTime(startTime, endTime);
        return leaveDataTable(dataTable, result);
    }

    /**
     * @param id
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 删除请假记录
     * @date 2019/1/27 20:30
     */
    @RequestMapping("removeLeave")
    @ResponseBody
    public BaseResult removeLeave(Integer id) {
        return leaveService.removeLeave(id);
    }

    /**
     * @param id
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 获取请假记录详情
     * @date 2019/1/28 15:23
     */
    @RequestMapping("getLeaveDetail")
    @ResponseBody
    public BaseResult getLeaveDetail(Integer id) {
        return leaveService.getLeaveDetailById(id);
    }

    /**
     * @param id
     * @param status
     * @param refusalReason
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 审批请假
     * @date 2019/1/28 21:58
     */
    @RequestMapping("examineLeave")
    @ResponseBody
    public BaseResult examineLeave(Integer id, Integer status, String refusalReason) {
        Leave leave = new Leave();
        leave.setId(id);
        leave.setAuditStatus(status);
        if (status == 2)
            leave.setRefusalReason(refusalReason);
        System.out.println(leave);
        return leaveService.examineLeave(leave);
    }

    /**
     * @param
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 获取未审批的请假记录
     * @date 2019/1/28 21:58
     */
    @RequestMapping("getNoExamineLeave")
    @ResponseBody
    public BaseResult getNoExamineLeave(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "1") Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<Leave> leaveList = leaveService.getLeaveByStatus("0");
        PageInfo<Leave> page = new PageInfo<>(leaveList);
        return new BaseResult<>(true, page);
    }

    /**
     * @param dataTable
     * @param result
     * @return top.flytop.studentsign.dto.DataTablePageUtil
     * @Description TODO dataTable通用封装
     * @Date 20/04/2019 18:09
     */
    private DataTablePageUtil leaveDataTable(DataTablePageUtil dataTable, List<Leave> result) {
        PageInfo<Leave> pageInfo = new PageInfo<>(result);
        //封装数据给DataTables
        dataTable.setData(pageInfo.getList());
        dataTable.setRecordsTotal((int) pageInfo.getTotal());
        dataTable.setRecordsFiltered(dataTable.getRecordsTotal());
        return dataTable;
    }
}
