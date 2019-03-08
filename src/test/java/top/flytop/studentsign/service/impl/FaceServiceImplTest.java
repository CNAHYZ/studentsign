package top.flytop.studentsign.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class FaceServiceImplTest {
    @Autowired
    private FaceServiceImpl faceService;

    @Test
    public void getUserFaceList() {
        String a = String.valueOf(faceService.getUserFaceList("23").getData());
        System.out.println(a);
    }
}