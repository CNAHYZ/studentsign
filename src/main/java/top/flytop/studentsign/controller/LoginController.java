package top.flytop.studentsign.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.utils.VerifyCodeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/2/10 12:49
 * @Version 1.0
 */
@Controller
public class LoginController {
    /**
     * @param request
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 用户使用密码登录方法
     * @Date 2018/12/29 22:10
     */
    @RequestMapping(value = "userLogin", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult userLogin(HttpServletRequest request, HttpServletResponse response) {
        String vCode = request.getParameter("validation");
        System.out.println("验证码是否正确：" + checkVCode(vCode, request));
        if (!checkVCode(vCode, request)) {
            return new BaseResult(false, 1, "验证码错误！");
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("rememberMe");
        boolean rememberMe = "on".equals(remember);
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(rememberMe);
            System.out.println("token:==========" + token);
            subject.login(token);
//            User currentUser = (User) subject.getPrincipal();
            request.getSession().setAttribute("currentUser", username);
            System.out.println(request.getSession().getAttribute("currentUser"));
            // 登录成功，返回视图
            if (subject.hasRole("student")) {
                // 学生类型
                return new BaseResult<>(true, "/student/index.html");
            } else if (subject.hasRole("admin")) {
                // 管理员类型
                return new BaseResult<>(true, "/admin/index.html");

            } else
                // 备用
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResult(false, 1, "用户名或密码错误！");
        }
    }


    /**
     * @param request
     * @param response
     * @return void
     * @Description TODO 获取验证码
     * @Date 2019/1/18 18:06
     */
    @RequestMapping("getVCode")
    @ResponseBody
    public void getVCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final Integer width = 155;
        final Integer height = 48;
        final Integer length = 4;
        HttpSession session = request.getSession();
        final OutputStream output = response.getOutputStream();
        String vCode = VerifyCodeUtil.outputVerifyImage(width, height, output, length);
        System.out.println(vCode);
        session.setAttribute("vCode", vCode);
    }

    /**
     * @param vCode
     * @param request
     * @return java.lang.Boolean
     * @Description TODO 验证验证码的正确性
     * @Date 2019/1/18 21:05
     */
    @RequestMapping("checkVCode")
    @ResponseBody
    public Boolean checkVCode(String vCode, HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println("=================" + vCode);
        System.out.println("----------------" + session.getAttribute("vCode"));
        return vCode != null && vCode.equalsIgnoreCase(session.getAttribute("vCode").toString());
    }

}
