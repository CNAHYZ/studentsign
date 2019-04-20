package top.flytop.studentsign.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.flytop.studentsign.pojo.Notice;

import java.util.List;

/**
 * @ClassName NoticeMapper
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/31 10:57
 * @Version 1.0
 */
@Repository
public interface NoticeMapper {
    Boolean saveNotice(Notice notice);

    List<Notice> getNoticeById(Integer noticeId);

    List<Notice> getNoticeByWord(String searchWord);

    Boolean updateNoticeById(Notice notice);

    Boolean removeNoticeById(Integer noticeId);

    Boolean changeNoticeStatus(@Param("noticeId") Integer noticeId, @Param("status") Integer status);


    List<Notice> getNoticeByStatus(@Param("status1") Integer status1, @Param("status2") Integer status2);
}
