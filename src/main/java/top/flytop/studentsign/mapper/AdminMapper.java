package top.flytop.studentsign.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.flytop.studentsign.pojo.Admin;

import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName AdminMapper
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 08/04/2019 09:39
 * @Version 1.0
 */
@Repository
public interface AdminMapper {

    List<Admin> getAdminList();

    int addAdmin(Admin admin);

    int updateAdminLoginTime(@Param("username") String username, @Param("lastLogin") Timestamp lastLogin);

    int removeAdminByName(@Param("username") String username);

    Admin getAdminByName(String username);

    int updateAdminByName(Admin admin);
}
