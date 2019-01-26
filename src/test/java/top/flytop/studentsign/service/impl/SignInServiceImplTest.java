package top.flytop.studentsign.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.flytop.studentsign.utils.ImageUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class SignInServiceImplTest {
    @Autowired
    private SignInServiceImpl signInService;
    @Autowired
    private ImageUtil imageUtil;

    @Test
    public void getSignRecord() {
        signInService.getSignRecord("0");
    }

    @Test
    public void testStudentSign() {
        String im = imageUtil.imageToBase64("D://2.jpg");
        System.out.println(signInService.studentSign(im));
    }
}