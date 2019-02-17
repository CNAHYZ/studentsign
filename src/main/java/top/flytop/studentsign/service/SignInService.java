package top.flytop.studentsign.service;

import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.pojo.SignIn;

import java.util.List;

/**
 * @ClassName SignRecordService
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/12 16:04
 * @Version 1.0
 */
public interface SignInService {
    BaseResult studentSign(String faceImage);

    List<SignIn> getSignRecord(String dayNum);

    List<SignIn> getPersonalSignRecord(String sNo, String startTime, String endTime);

    List<SignIn> getSignRecordFilter(String startTime, String endTime);

    List<SignIn> getSignRecordByKeyword(String keyword);
}
