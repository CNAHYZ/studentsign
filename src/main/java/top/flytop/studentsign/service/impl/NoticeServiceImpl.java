package top.flytop.studentsign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.mapper.NoticeMapper;
import top.flytop.studentsign.pojo.Notice;
import top.flytop.studentsign.service.NoticeService;

import java.util.List;
import java.util.Map;

/**
 * @ClassName NoticeServiceImpl
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/30 18:24
 * @Version 1.0
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    private NoticeMapper noticeMapper;

    @Autowired
    private void noticeMapper(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    /**
     * @param notice
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/1/31 11:10
     */
    @Override
    public BaseResult saveNotice(Notice notice) {
        return noticeMapper.saveNotice(notice) ?
                (notice.getStatus() == 0 ? new BaseResult<>(true, "通知发布成功！") : new BaseResult<>(true, "通知仅保存成功！")) :
                new BaseResult(false, 1, "通知保存失败，请重试！");
    }

    /**
     * @return java.util.List<top.flytop.studentsign.pojo.Notice>
     * @Description TODO
     * @Date 2019/1/31 20:16
     */
    @Override
    public List<Notice> getNotice(Map paramMap) {
        System.out.println("paraMap:" + paramMap);
        if (paramMap.containsKey("noticeId")) {
            return noticeMapper.getNoticeById((Integer) paramMap.get("noticeId"));
        } else if (paramMap.containsKey("searchWord")) {
            return noticeMapper.getNoticeByWord(paramMap.get("searchWord").toString());
        } else if (paramMap.containsKey("status1"))
            return noticeMapper.getNoticeByStatus((Integer) paramMap.get("status1"), (Integer) paramMap.get("status2"));
        throw new RuntimeException();
    }

    /**
     * @param noticeId
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/1/31 20:23
     */
    @Override
    public BaseResult removeNotice(Integer noticeId) {
        return noticeMapper.removeNoticeById(noticeId) ?
                new BaseResult<>(true, "删除成功") :
                new BaseResult(false, 1, "删除失败，请重试！");
    }

    /**
     * @param notice
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/1/31 20:16
     */
    @Override
    public BaseResult updateNotice(Notice notice) {
        return noticeMapper.updateNoticeById(notice) ?
                new BaseResult<>(true, "修改成功！") :
                new BaseResult(false, 1, "修改失败，请稍后再试！");
    }

    /**
     * @param noticeId
     * @param status
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/2/1 15:32
     */
    @Override
    public BaseResult changeNoticeStatus(Integer noticeId, Integer status) {
        return noticeMapper.changeNoticeStatus(noticeId, status) ?
                new BaseResult<>(true, "状态修改成功！") :
                new BaseResult(false, 1, "修改失败，请稍后再试！");
    }

}
