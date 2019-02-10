package top.flytop.studentsign.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @ClassName Leave
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/27 11:41
 * @Version 1.0
 */
@Alias("leave")
public class Leave implements Serializable {
    private Integer id;
    private String sNo;
    private Timestamp applicationTime;
    private Integer category;
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date startTime;
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date endTime;
    private String comment;
    private String attachment;
    private Integer auditStatus;
    private String refusalReason;
    private String sName;

    public Leave() {
    }

    public Leave(Integer id, String sNo, Timestamp applicationTime, Integer category, Date startTime, Date endTime, String comment, String attachment, Integer auditStatus, String refusalReason, String sName) {
        this.id = id;
        this.sNo = sNo;
        this.applicationTime = applicationTime;
        this.category = category;
        this.startTime = startTime;
        this.endTime = endTime;
        this.comment = comment;
        this.attachment = attachment;
        this.auditStatus = auditStatus;
        this.refusalReason = refusalReason;
        this.sName = sName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public Timestamp getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Timestamp applicationTime) {
        this.applicationTime = applicationTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getRefusalReason() {
        return refusalReason;
    }

    public void setRefusalReason(String refusalReason) {
        this.refusalReason = refusalReason;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Leave{" +
                "id=" + id +
                ", sNo='" + sNo + '\'' +
                ", applicationTime=" + applicationTime +
                ", category=" + category +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", comment='" + comment + '\'' +
                ", attachment='" + attachment + '\'' +
                ", auditStatus=" + auditStatus +
                ", refusalReason='" + refusalReason + '\'' +
                ", sName='" + sName + '\'' +
                '}';
    }
}
