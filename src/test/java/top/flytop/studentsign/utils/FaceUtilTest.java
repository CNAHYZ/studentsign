package top.flytop.studentsign.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.flytop.studentsign.dto.BaseResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class FaceUtilTest {
    @Autowired
    private FaceUtil faceUtil;
    @Autowired
    private ImageUtil imageUtil;

    @Test
    public void getClient() {
    }

    @Test
    public void getUserFace() {
    }

    @Test
    public void faceChecker() {
        String im = imageUtil.imageToBase64("D://3.jpg");
        BaseResult Result = faceUtil.faceChecker(im);
        System.out.println(Result);
    }

    @Test
    public void faceSearch() {
        String im = imageUtil.imageToBase64("D://3.jpg");
        System.out.println(faceUtil.faceSearch(im, null));
    }

    @Test
    public void faceSearch1() {
    }

    @Test
    public void faceReg() {
    }

    @Test
    public void faceRemove() {
    }
}