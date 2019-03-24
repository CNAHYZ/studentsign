package top.flytop.studentsign.quartz;

/**
 * @ClassName QuartzJobExample
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/3/23 11:39
 * @Version 1.0
 */

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * quartz示例定时器类
 *
 * @author Administrator
 */
@Component
public class QuartzJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            ServletContext servletContext = webApplicationContext.getServletContext();
            String triggerName = jobExecutionContext.getTrigger().getKey().getName();

            if ("startTimeSetting".equals(triggerName)) {
//                System.out.println(jobExecutionContext.getTrigger());
                servletContext.setAttribute("whetherSign", true);
                System.out.println("==============系统开放★★★★★★★★★★★");
            } else if ("endTimeSetting".equals(triggerName)) {
                servletContext.setAttribute("whetherSign", false);
                System.out.println("==============系统关闭★★★★★★★★★★★");
            }
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "★★★★★★★★★★★");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}