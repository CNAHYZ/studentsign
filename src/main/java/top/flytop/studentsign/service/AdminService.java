package top.flytop.studentsign.service;

import top.flytop.studentsign.dto.BaseResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AdminService
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 08/04/2019 11:01
 * @Version 1.0
 */
public interface AdminService {
    BaseResult getAdminList();

    BaseResult addAdmin(HttpServletRequest request);

    BaseResult removeAdmin(String username);

    void updateAdminLoginTime(String username);

    BaseResult getAdmin(String username);

    BaseResult updateAdmin(HttpServletRequest request);
}
