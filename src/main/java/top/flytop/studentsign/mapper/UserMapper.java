package top.flytop.studentsign.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.flytop.studentsign.pojo.User;

import java.util.List;


@Repository
public interface UserMapper {

    User getUser(String username);

    User userLogin(@Param("username") String username, @Param("pwd") String pwd);

    Integer deleteUser(@Param("array") String[] sNos, @Param("type") Integer type);

    Boolean changePwdByUsername(@Param("pwd") String pwd, @Param("username") String username);

    Integer addUser(User user);

    Integer resetUserPwd(List<User> list);
}
