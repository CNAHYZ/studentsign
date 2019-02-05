package top.flytop.studentsign.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class NoticeMapperTest {
    @Autowired
    private NoticeMapper noticeMapper;

    @Test
    public void removeNoticeById() {
        System.out.println(noticeMapper.removeNoticeById(null));
    }

    @Test
    public void getNoticeByStatus() {
        System.out.println(noticeMapper.getNoticeByStatus(2, null));
    }

    @Test
    public void test() {
        Map<String, Object> paraMap = new HashMap<>();
        System.out.println(paraMap.get("1"));
        ;
    }

}