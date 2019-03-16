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
    private FileUtil fileUtil;

    @Test
    public void getClient() {
    }

    @Test
    public void getUserFace() {
        BaseResult result = faceUtil.getUserFace("23");
        System.out.println(result);
    }

    @Test
    public void faceChecker() {
        String im = fileUtil.imageToBase64("D://3.jpg");
        BaseResult result = faceUtil.faceChecker(im);
        System.out.println(result);
    }

    @Test
    public void faceSearch() {
        String im = fileUtil.imageToBase64("D://3.jpg");
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
        faceUtil.faceRemove("23", "e85705ce61411198848ec96d22a37c8c");
    }

}