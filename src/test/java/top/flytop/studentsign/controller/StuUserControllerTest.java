package top.flytop.studentsign.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.flytop.studentsign.controller.admin.UserController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class StuUserControllerTest {
    private UserController userController = new UserController();

    @Test
    public void testUserLogin() {

    }
}