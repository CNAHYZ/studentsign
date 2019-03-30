package top.flytop.studentsign.service;

import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.pojo.OpenTime;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName SettingService
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/3/23 17:30
 * @Version 1.0
 */
public interface SettingService {
    BaseResult saveOpenTime(OpenTime openTime, HttpServletRequest request);

    Map getOpenTime();
}
