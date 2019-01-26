package top.flytop.studentsign.service;

import top.flytop.studentsign.dto.BaseResult;

/**
 * @ClassName SignRecordService
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/12 16:04
 * @Version 1.0
 */
public interface SignInService {
    BaseResult studentSign(String faceImage);

    BaseResult getSignRecord(String dayNum);

    BaseResult getSignRecordFilter(String startTime, String endTime);

    BaseResult getSignRecordByKeyword(String keyword);
}
