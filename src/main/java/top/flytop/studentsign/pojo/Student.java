package top.flytop.studentsign.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Date;

@Component("student")
@Alias(value = "student")
public class Student implements Serializable {
    Integer id;
    String sNo;
    String sName;
    String gender;
    @JSONField(format = "yyyy-MM-dd")//自定义json转换日期的格式
            Date birthday;
    String buildingNo;
    String roomNo;
    String faceImage;

    public Student() {
    }

    public Student(Integer id, String sNo, String sName, String gender, Date birthday, String buildingNo, String roomNo, String faceImage) {
        this.id = id;
        this.sNo = sNo;
        this.sName = sName;
        this.gender = gender;
        this.birthday = birthday;
        this.buildingNo = buildingNo;
        this.roomNo = roomNo;
        this.faceImage = faceImage;
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

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
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

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", sNo='" + sNo + '\'' +
                ", sName='" + sName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", buildingNo='" + buildingNo + '\'' +
                ", roomNo='" + roomNo + '\'' +
                ", faceImage='" + faceImage + '\'' +
                '}';
    }
}
