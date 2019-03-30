package top.flytop.studentsign.quartz;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import top.flytop.studentsign.mapper.SettingMapper;
import top.flytop.studentsign.pojo.OpenTime;
import top.flytop.studentsign.service.impl.SettingServiceImpl;

import javax.servlet.ServletContext;
import java.util.Date;

/**
 * @ClassName QuartzInit
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/3/24 16:08
 * @Version 1.0
 */
@Component
@Lazy(false)
public class QuartzInit implements ServletContextAware {
    private SettingMapper settingMapper;
    private SettingServiceImpl settingService;

    @Autowired
    private void settingMapper(SettingMapper settingMapper) {
        this.settingMapper = settingMapper;
    }

    @Autowired
    private void settingService(SettingServiceImpl settingService) {
        this.settingService = settingService;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        System.out.println("===========setServletContext方法执行==============");
        servletContext.setAttribute("whetherSign", false);
        String JOB_GROUP_NAME = "JOBGROUP_NAME";
        String TRIGGER_GROUP_NAME = "TRIGGERGROUP_NAME";
        OpenTime openTime = settingMapper.getOpenTime();
        try {
            QuartzManager.addJob("startTimeSetting", JOB_GROUP_NAME, "startTimeSetting", TRIGGER_GROUP_NAME,
                    QuartzJob.class, openTime.getStartCronExpr());
            System.out.println("【openTimeSetting】[开始]任务已添加");
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();

            settingService.judgeOpenTime(openTime, servletContext);

            Scheduler sf = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey("startTimeSetting", TRIGGER_GROUP_NAME);
            CronTrigger trigger = (CronTrigger) sf.getTrigger(triggerKey);
            System.out.println("----------------" + trigger.getFireTimeAfter(new Date()));

            QuartzManager.addJob("endTimeSetting", JOB_GROUP_NAME, "endTimeSetting", TRIGGER_GROUP_NAME,
                    QuartzJob.class, openTime.getEndCronExpr());
            System.out.println("【openTimeSetting】[结束]任务已添加");

            TriggerKey triggerKey1 = TriggerKey.triggerKey("endTimeSetting", TRIGGER_GROUP_NAME);
            CronTrigger trigger1 = (CronTrigger) sf.getTrigger(triggerKey1);
            System.out.println("----------------" + trigger1.getFireTimeAfter(new Date()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
