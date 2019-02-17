package top.flytop.studentsign.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * @ClassName LogInterceptor
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/16 14:10
 * @Version 1.0
 */
@Component
public class LogInterceptor {
    /**
     * 因为我这引入的是slf4j与log4j的桥接包，即可用log4j也可用slf4j输出日志
     * log4j：
     * import org.apache.logging.log4j.LogManager;
     * import org.apache.logging.log4j.Logger;
     * slf4j:
     * import org.slf4j.Logger;
     * import org.slf4j.LoggerFactory;
     */
    private static final Logger logger = LogManager.getLogger(LogInterceptor.class);
//    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    String logStr = null;
//    //前置通知
//    public void before(JoinPoint jp){
//        logStr=jp.getTarget().getClass().getName()+"类的"
//                +jp.getSignature().getName()+"方法开始执行******Start******";
//        logger.info(logStr);
//    }
//      //最终通知
//    public void after(JoinPoint jp){
//        logStr=jp.getTarget().getClass().getName()+"类的"
//                +jp.getSignature().getName()+"方法执行结束******End******";
//        logger.info(logStr);
//    }
//      //异常抛出后通知
//    public void afterThrowing(JoinPoint call){
//        String className = call.getTarget().getClass().getName();
//        String methodName = call.getSignature().getName();
//        System.out.println(className+"."+methodName+"()方法抛出了异常...");
//    }
//     //后置通知
//    public void afterReturn(JoinPoint call){
//        String className = call.getTarget().getClass().getName();
//        String methodName = call.getSignature().getName();
//        System.out.println(className+"."+methodName+"()方法正常执行结束...");
//    }


    //用来做环绕通知的方法可以第一个参数定义为org.aspectj.lang.ProceedingJoinPoint类型
    public Object around(ProceedingJoinPoint call) throws Throwable {
        Object result = null;

        //取得类名和方法名
        String className = call.getTarget().getClass().getName();
        String methodName = call.getSignature().getName();

        //相当于前置通知
        logStr = className + "类的" + methodName + "方法开始执行******Start******";
        logger.info(logStr);
        try {
            result = call.proceed();
            //相当于后置通知
            logStr = className + "." + methodName + "()方法正常执行结束";
            logger.info(logStr);
        } catch (Throwable e) {
            //相当于异常抛出后通知
            StackTraceElement stackTraceElement = e.getStackTrace()[e.getStackTrace().length - 1];

            Object[] args = call.getArgs();

            logger.error("----------------------------------------------------------------------------------");
            logger.error("===执行{}类的{}()方法的{}行", className, methodName, stackTraceElement.getLineNumber());
            logger.error("===异常信息为：{}  ", e.fillInStackTrace().toString());
            logger.error("===参数信息为：{}  ", args);
            e.printStackTrace();
            throw e;
        } finally {
            //相当于最终通知
            logStr = className + "类的" + methodName + "方法执行结束*******End*******";
            logger.info(logStr);
        }
        return result;
    }

}

