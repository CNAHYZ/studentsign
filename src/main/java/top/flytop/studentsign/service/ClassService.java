package top.flytop.studentsign.service;

import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.pojo.SClass;

/**
 * @ClassName ClassService
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 11/04/2019 16:17
 * @Version 1.0
 */

public interface ClassService {
    BaseResult getClassList();

    BaseResult addClass(SClass sClass);

    BaseResult removeClass(String cNo);

    BaseResult getClass(String cNo);

    BaseResult updateClassInfo(SClass sClass);

}
