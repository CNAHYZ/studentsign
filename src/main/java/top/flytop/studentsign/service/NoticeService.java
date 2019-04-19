package top.flytop.studentsign.service;

import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.pojo.Notice;

import java.util.List;
import java.util.Map;

/**
 * @ClassName NoticeService
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/30 18:24
 * @Version 1.0
 */
public interface NoticeService {
    BaseResult saveNotice(Notice notice);

    List<Notice> getNotice(Map paramMap);

    List<Notice> getNoticeTitle();

    BaseResult removeNotice(Integer noticeId);

    BaseResult updateNotice(Notice notice);

    BaseResult changeNoticeStatus(Integer noticeId, Integer status);
}
