package top.flytop.studentsign.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.flytop.studentsign.pojo.SignIn;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class SignInMapperTest {
    @Autowired
    private SignInMapper signInMapper;

    @Test
    public void testSignRecordTime() {
        List<SignIn> list = signInMapper.signRecordTime("2018-01-01", "2019-02-01");
        System.out.println(list);
    }

    @Test
    public void testPersonalSignRecordTime() {
        List<SignIn> list = signInMapper.personalSignRecordTime("234", "2018-01-01", "2019-02-01");
        System.out.println(list);
    }
}