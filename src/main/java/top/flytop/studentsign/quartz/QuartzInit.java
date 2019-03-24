package top.flytop.studentsign.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import top.flytop.studentsign.mapper.SettingMapper;
import top.flytop.studentsign.pojo.OpenTime;

import javax.servlet.ServletContext;

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

    @Autowired
    private void settingMapper(SettingMapper settingMapper) {
        this.settingMapper = settingMapper;
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

            QuartzManager.addJob("endTimeSetting", JOB_GROUP_NAME, "endTimeSetting", TRIGGER_GROUP_NAME,
                    QuartzJob.class, openTime.getEndCronExpr());
            System.out.println("【openTimeSetting】[结束]任务已添加");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
