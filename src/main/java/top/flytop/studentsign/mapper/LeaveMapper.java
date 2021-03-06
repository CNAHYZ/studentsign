package top.flytop.studentsign.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.flytop.studentsign.pojo.Leave;

import java.util.List;

/**
 * @ClassName LeaveMapper
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/26 20:51
 * @Version 1.0
 */
@Repository
public interface LeaveMapper {
    Boolean addLeave(Leave leave);

    Boolean removeLeaveById(Integer id);

    List<Leave> getLeaveByStatus(String status);

    List<Leave> getLeaveByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<Leave> getLeave(String dayNum);

    Leave getLeaveDetailById(Integer id);

    Boolean updateAuditStatus(Leave leave);

    List<Leave> getLeaveBySNo(@Param("sNo") String sNo, @Param("auditStatus") Integer auditStatus);

    List<Leave> getLeaveListByKeyword(@Param("keyword") String keyword);
}
