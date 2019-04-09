package top.flytop.studentsign.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.mapper.AdminMapper;
import top.flytop.studentsign.mapper.UserMapper;
import top.flytop.studentsign.pojo.Admin;
import top.flytop.studentsign.pojo.User;
import top.flytop.studentsign.utils.ShiroMd5;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * @ClassName AdminServiceImpl
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 08/04/2019 11:01
 * @Version 1.0
 */
@Service
public class AdminServiceImpl implements AdminService {
    private AdminMapper adminMapper;
    private UserMapper userMapper;

    @Autowired
    private void adminMapper(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Autowired
    private void userMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * @param
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 获取普通管理员列表
     * @Date 08/04/2019 12:07
     */
    @Override
    public BaseResult getAdminList() {
        return BaseResult.success(adminMapper.getAdminList());
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 添加管理员
     * @Date 08/04/2019 14:38
     */
    @Override
    public BaseResult addAdmin(HttpServletRequest request) {

        Admin admin = new Admin();
        admin.setUsername(request.getParameter("username"));
        admin.setRealName(request.getParameter("realName"));
        admin.setPhoneNum(request.getParameter("phoneNum"));
        admin.setComment(request.getParameter("comment"));

        String salt = String.valueOf(System.currentTimeMillis());
        String pwdEncrypt = ShiroMd5.md5Encrypt(request.getParameter("pwd"), salt);

        User user = new User();
        user.setUsername(admin.getUsername());
        user.setPwd(pwdEncrypt);
        //普通管理员类型
        user.setType(2);
        user.setSalt(salt);

        try {
            adminMapper.addAdmin(admin);
            userMapper.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加管理员失败！");
        }
        return BaseResult.success("添加成功！");
    }

    /**
     * @param username
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 删除管理员
     * @Date 08/04/2019 12:07
     */
    @Override
    public BaseResult removeAdmin(String username) {
        String[] names = {username};
        try {
            adminMapper.removeAdminByName(username);
            userMapper.deleteUser(names, 2);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除管理员失败！");
        }
        return BaseResult.success("删除成功！");
    }

    /**
     * @return void
     * @Description TODO 更新管理员的最后一次登录时间
     * @Date 08/04/2019 14:51
     */
    @Override
    public void updateAdminLoginTime(String username) {
        adminMapper.updateAdminLoginTime(username, new Timestamp(System.currentTimeMillis()));
    }

    /**
     * @param username
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 根据username获取admin
     * @Date \ 23:14
     */
    @Override
    public BaseResult getAdmin(String username) {
        return BaseResult.success(adminMapper.getAdminByName(username));
    }

    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 更新admin
     * @Date 08/04/2019 23:14
     */
    @Override
    public BaseResult updateAdmin(HttpServletRequest request) {
        Admin admin = new Admin();
        admin.setUsername(request.getParameter("username"));
        admin.setRealName(request.getParameter("realName"));
        admin.setPhoneNum(request.getParameter("phoneNum"));
        admin.setComment(request.getParameter("comment"));

        adminMapper.updateAdminByName(admin);
        return BaseResult.success("修改成功！");
    }

}
