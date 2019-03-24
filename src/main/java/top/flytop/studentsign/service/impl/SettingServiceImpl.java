package top.flytop.studentsign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.mapper.SettingMapper;
import top.flytop.studentsign.pojo.OpenTime;
import top.flytop.studentsign.quartz.QuartzManager;
import top.flytop.studentsign.service.SettingService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName SettingServiceImpl
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/3/23 17:30
 * @Version 1.0
 */
@Service
public class SettingServiceImpl implements SettingService {
    private SettingMapper settingMapper;

    @Autowired
    public void settingMapper(SettingMapper settingMapper) {
        this.settingMapper = settingMapper;
    }

    /**
     * @param openTime
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 保存设置的开放时间
     * @Date 2019/3/23 17:42
     */
    @Override
    public BaseResult saveOpenTime(OpenTime openTime) {
        String JOB_GROUP_NAME = "JOBGROUP_NAME";
        String TRIGGER_GROUP_NAME = "TRIGGERGROUP_NAME";

        Map startMap = getHourMinute(openTime.getTimeStart());
        Map endMap = getHourMinute(openTime.getTimeEnd());
        String weekday = openTime.getWeekday();

        /*拼接cron表达式*/
        String startCronExpr = "0 " + startMap.get("minute") + " " +
                startMap.get("hour") + " ? * " + weekday;
        String endCronExpr = "0 " + endMap.get("minute") + " " +
                endMap.get("hour") + " ? * " + weekday;

        /*保存到对象*/
        openTime.setStartCronExpr(startCronExpr);
        openTime.setEndCronExpr(endCronExpr);
        System.out.println(openTime);

        System.out.println("【修改开放时间】...");

        QuartzManager.modifyJobTime("startTimeSetting", JOB_GROUP_NAME,
                "startTimeSetting", TRIGGER_GROUP_NAME, startCronExpr);
        QuartzManager.modifyJobTime("endTimeSetting", JOB_GROUP_NAME,
                "endTimeSetting", TRIGGER_GROUP_NAME, endCronExpr);

        if (settingMapper.saveOpenTime(openTime) > 0)
            return BaseResult.success("设置成功！");
        else return BaseResult.fail(1, "保存失败，请稍后重试！");
    }

    /**
     * @param
     * @return top.flytop.studentsign.pojo.OpenTime
     * @Description TODO 获取设置的时间
     * @Date 2019/3/24 12:48
     */
    @Override
    public Map getOpenTime() {

        OpenTime openTime = settingMapper.getOpenTime();
        System.out.println(openTime);
        /*将字符串转为数组*/
        String[] weekday = openTime.getWeekday().split(",");
        System.out.println(Arrays.toString(weekday));

        /*保存KV至Map*/
        Map<String, Object> map = new HashMap<>();
        map.put("weekday", weekday);
        map.put("timeStart", openTime.getTimeStart());
        map.put("timeEnd", openTime.getTimeEnd());
        return map;
    }

    /**
     * @param timeString
     * @return java.util.Map<java.lang.String, java.lang.Integer>
     * @Description TODO 根据给定的字符串取出小时和分钟数
     * @Date 2019/3/23 17:09
     */
    private Map<String, Integer> getHourMinute(String timeString) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = sdf.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Map<String, Integer> map = new HashMap<>();
        map.put("hour", calendar.get(Calendar.HOUR_OF_DAY));
        map.put("minute", calendar.get(Calendar.MINUTE));
        return map;
    }
}
