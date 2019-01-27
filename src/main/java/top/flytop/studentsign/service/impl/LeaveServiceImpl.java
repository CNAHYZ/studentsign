package top.flytop.studentsign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.mapper.LeaveMapper;
import top.flytop.studentsign.pojo.Leave;
import top.flytop.studentsign.service.LeaveService;

import java.util.List;

/**
 * @ClassName LeaveServiceImpl
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/27 13:51
 * @Version 1.0
 */
@Service
public class LeaveServiceImpl implements LeaveService {
    private LeaveMapper leaveMapper;

    @Autowired
    private void leaveMapper(LeaveMapper leaveMapper) {
        this.leaveMapper = leaveMapper;
    }

    /**
     * @param dayNum
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/27 19:50
     */
    @Override
    public BaseResult getLeave(String dayNum) {
        List list = leaveMapper.getLeave(dayNum);
        System.out.println(list);
        return new BaseResult<>(true, list);
    }

    /**
     * @param status
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/27 19:50
     */
    @Override
    public BaseResult getLeaveByStatus(String status) {
        List list = leaveMapper.getLeaveByStatus(status);
        System.out.println(list);
        return new BaseResult<>(true, list);
    }

    /**
     * @param id
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/27 20:37
     */
    @Override
    public BaseResult removeLeave(Integer id) {
        Boolean delResult = leaveMapper.removeLeaveById(id);
        if (delResult)
            return new BaseResult<String>(true, "删除成功！");
        else return new BaseResult(false, 1, "删除失败，请重试！");
    }

    /**
     * @param id
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/27 20:37
     */
    @Override
    public BaseResult getLeaveDetailById(Integer id) {
        Leave leaveDetail = leaveMapper.getLeaveDetailById(id);
        if (leaveDetail != null)
            return new BaseResult<>(true, leaveDetail);
        else return new BaseResult(false, 1, "没有该条信息！");
    }

}
