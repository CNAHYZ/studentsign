package top.flytop.studentsign.pojo;

import org.apache.ibatis.type.Alias;

/**
 * @ClassName OpenTime
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/3/23 12:44
 * @Version 1.0
 */
@Alias("openTime")
public class OpenTime {
    private String weekday;
    private String timeStart;
    private String timeEnd;
    private String startCronExpr;
    private String endCronExpr;

    public OpenTime() {
    }

    public OpenTime(String weekday, String timeStart, String timeEnd, String startCronExpr, String endCronExpr) {
        this.weekday = weekday;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.startCronExpr = startCronExpr;
        this.endCronExpr = endCronExpr;
    }

    public OpenTime(String weekday, String timeStart, String timeEnd) {
        this.weekday = weekday;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getStartCronExpr() {
        return startCronExpr;
    }

    public void setStartCronExpr(String startCronExpr) {
        this.startCronExpr = startCronExpr;
    }

    public String getEndCronExpr() {
        return endCronExpr;
    }

    public void setEndCronExpr(String endCronExpr) {
        this.endCronExpr = endCronExpr;
    }

    @Override
    public String toString() {
        return "OpenTime{" +
                "weekday=" + weekday + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", startCronExpr='" + startCronExpr + '\'' +
                ", endCronExpr='" + endCronExpr + '\'' +
                '}';
    }
}
