package top.flytop.studentsign.pojo;

import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

/**
 * @ClassName Notice
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/30 22:00
 * @Version 1.0
 */
@Alias("notice")
public class Notice {
    private Integer noticeId;
    private String title;
    private String keyword;
    private String content;
    private Timestamp createTime;
    private Integer status;

    public Notice() {
    }

    public Notice(Integer noticeId, String title, String keyword, String content, Timestamp createTime, Integer status) {
        this.noticeId = noticeId;
        this.title = title;
        this.keyword = keyword;
        this.content = content;
        this.createTime = createTime;
        this.status = status;
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "noticeId=" + noticeId +
                ", title='" + title + '\'' +
                ", keyword='" + keyword + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                '}';
    }
}
