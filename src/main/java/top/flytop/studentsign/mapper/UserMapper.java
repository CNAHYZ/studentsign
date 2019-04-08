package top.flytop.studentsign.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.flytop.studentsign.pojo.User;


@Repository
public interface UserMapper {

    User getUser(String username);

    User userLogin(@Param("username") String username, @Param("pwd") String pwd);

    Integer deleteUser(@Param("array") String[] sNos, @Param("type") Integer type);

    Boolean changePwdByUsername(@Param("pwd") String pwd, @Param("username") String username);

    Integer initAllUsers(@Param("pwd") String pwd, @Param("salt") String salt);

    Integer addUser(User user);

    Integer batchInitUser(@Param("array") String[] username, @Param("salt") String salt, @Param("pwd") String pwd);
}
