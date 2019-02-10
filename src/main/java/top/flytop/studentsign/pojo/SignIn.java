package top.flytop.studentsign.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @ClassName SignIn
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/6 14:31
 * @Version 1.0
 */
@Alias("signIn")
public class SignIn implements Serializable {
    private Integer id;
    private String sNo;
    private Integer signStatus;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp signTime;
    private String sName;

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public SignIn() {
    }

    public SignIn(Integer id, String sNo, Integer signStatus, Timestamp signTime, String sName) {
        this.id = id;
        this.sNo = sNo;
        this.signStatus = signStatus;
        this.signTime = signTime;
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

    public Integer getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Integer signStatus) {
        this.signStatus = signStatus;
    }

    public Timestamp getSignTime() {
        return signTime;
    }

    public void setSignTime(Timestamp signTime) {
        this.signTime = signTime;
    }

    @Override
    public String toString() {
        return "SignIn{" +
                "id=" + id +
                ", sNo='" + sNo + '\'' +
                ", signStatus=" + signStatus +
                ", signTime=" + signTime +
                ", sName='" + sName + '\'' +
                '}';
    }
}
