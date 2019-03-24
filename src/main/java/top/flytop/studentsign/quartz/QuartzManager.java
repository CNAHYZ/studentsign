package top.flytop.studentsign.quartz;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName QuartzManager
 * @Description TODO Quartz调度管理器
 * @Auther Wonder-yz
 * @Date 2019/3/23 11:40
 * @Version 1.0
 */
@Component
public class QuartzManager {
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    /**
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass         任务
     * @param cron             时间设置，参考quartz说明文档
     * @return void
     * @Description TODO 添加一个定时任务
     * @Date 2019/3/23 12:15
     */
    public static void addJob(String jobName, String jobGroupName,
                              String triggerName, String triggerGroupName, Class jobClass, String cron) {
        try {
            Scheduler sf = schedulerFactory.getScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            /* 触发器时间设定(设置misfire机制：withMisfireHandlingInstructionFireAndProceed
            ——以当前时间为触发频率立刻触发一次执行
            ——然后按照Cron频率依次执行)*/
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron)
                    .withMisfireHandlingInstructionFireAndProceed());
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();

            // 调度容器设置JobDetail和Trigger
            sf.scheduleJob(jobDetail, trigger);

            // 启动
            if (!sf.isShutdown()) {
                sf.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param jobName
     * @param jobGroupName
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param cron             时间设置，参考quartz说明文档
     * @return void
     * @Description TODO 修改一个任务的触发时间
     * @Date 2019/3/23 12:14
     */
    public static void modifyJobTime(String jobName, String jobGroupName,
                                     String triggerName, String triggerGroupName, String cron) {
        try {
            Scheduler sf = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) sf.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }

            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                /* 方式一 ：调用 rescheduleJob 开始 */
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                sf.rescheduleJob(triggerKey, trigger);
                /* 方式一 ：调用 rescheduleJob 结束 */

                /* 方式二：先删除，然后在创建一个新的Job  */
                //JobDetail jobDetail = sf.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                //Class<? extends Job> jobClass = jobDetail.getJobClass();
                //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
                /* 方式二 ：先删除，然后在创建一个新的Job */
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @return void
     * @Description TODO 移除一个任务
     * @Date 2019/3/23 12:14
     */
    public static void removeJob(String jobName, String jobGroupName,
                                 String triggerName, String triggerGroupName) {
        try {
            Scheduler sf = schedulerFactory.getScheduler();

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            sf.pauseTrigger(triggerKey);// 停止触发器
            sf.unscheduleJob(triggerKey);// 移除触发器
            sf.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param
     * @return void
     * @Description TODO 启动所有定时任务
     * @Date 2019/3/23 12:15
     */
    public static void startJobs() {
        try {
            Scheduler sf = schedulerFactory.getScheduler();
            sf.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param
     * @return void
     * @Description TODO 关闭所有定时任务
     * @Date 2019/3/23 12:15
     */
    public static void shutdownJobs() {
        try {
            Scheduler sf = schedulerFactory.getScheduler();
            if (!sf.isShutdown()) {
                sf.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}