package top.flytop.studentsign.mapper;

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

    List<Leave> getLeave(String dayNum);

    Leave getLeaveDetailById(Integer id);
}
