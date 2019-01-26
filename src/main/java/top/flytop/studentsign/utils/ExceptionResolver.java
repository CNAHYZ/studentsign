package top.flytop.studentsign.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import top.flytop.studentsign.dto.BaseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName ExceptionResolver
 * @Description TODO 异常处理类
 * @Auther Wonder-yz
 * @Date 2019/1/2 09:32
 * @Version 1.0
 */
@Order(-1000)
public class ExceptionResolver implements HandlerExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger("exceptionLog");

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {
        BaseResult result = new BaseResult();
        StringBuilder sb = new StringBuilder();

        //处理异常
        if (ex instanceof BussinessException) {
            resolverBussinessException(ex, sb, result);
        } else if (ex instanceof BindException) {
            resolverBindException(ex, sb, result);
        } else {
            resolverOtherException(ex, sb, result);
        }

        result.setErrCode(0);
        result.setData(sb);
//        result.setTime(new Date());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException e) {
            logger.error("与客户端通讯异常：" + e.getMessage(), e);
            e.printStackTrace();
        }

        logger.debug("异常：" + ex.getMessage(), ex);
        ex.printStackTrace();
        return new ModelAndView();
    }

    /*
     * 处理业务层异常
     */
    private void resolverBussinessException(Exception ex, StringBuilder sb, BaseResult result) {
        BussinessException businessException = (BussinessException) ex;
        sb.append(businessException.getMsg());
        addResult(result, "业务异常");
    }

    /*
     * 处理参数绑定异常
     */
    private void resolverBindException(Exception ex, StringBuilder sb, BaseResult result) {
        BindException be = (BindException) ex;
        List<FieldError> errorList = be.getBindingResult().getFieldErrors();
        for (FieldError error : errorList) {
            sb.append(error.getObjectName());
            sb.append("对象的");
            sb.append(error.getField());
            sb.append("字段");
            sb.append(error.getDefaultMessage());
        }
        addResult(result, "参数传递异常");
    }

    /*
     * 处理其他异常
     */
    private void resolverOtherException(Exception ex, StringBuilder sb, BaseResult result) {
        sb.append(ex.getMessage());
        addResult(result, "其他异常");
    }

    /*
     * 封装code和msg
     */
    private void addResult(BaseResult result, String msg) {
        result.setErrMsg(msg);
    }

}
