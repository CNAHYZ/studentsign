package top.flytop.studentsign.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Date;

@Component("student")
@Alias(value = "student")
public class Student implements Serializable {
    private String sNo;
    private String sName;
    private String cNo;
    private String gender;
    @JSONField(format = "yyyy-MM-dd")//自定义json转换日期的格式
    private Date birthday;
    private String buildingNo;
    private String roomNo;
    private String phoneNum;

    private SClass sClass;

    public Student() {
    }

    public Student(String sNo, String sName, String cNo, String gender, Date birthday, String buildingNo, String roomNo, String phoneNum) {
        this.sNo = sNo;
        this.sName = sName;
        this.cNo = cNo;
        this.gender = gender;
        this.birthday = birthday;
        this.buildingNo = buildingNo;
        this.roomNo = roomNo;
        this.phoneNum = phoneNum;
    }

    public SClass getsClass() {
        return sClass;
    }

    public void setsClass(SClass sClass) {
        this.sClass = sClass;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getcNo() {
        return cNo;
    }

    public void setcNo(String cNo) {
        this.cNo = cNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sNo='" + sNo + '\'' +
                ", sName='" + sName + '\'' +
                ", cNo='" + cNo + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", buildingNo='" + buildingNo + '\'' +
                ", roomNo='" + roomNo + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", sClass=" + sClass +
                '}';
    }
}
