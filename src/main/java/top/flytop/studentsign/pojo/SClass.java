package top.flytop.studentsign.pojo;

import org.apache.ibatis.type.Alias;

/**
 * @ClassName SClass
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/3/6 17:12
 * @Version 1.0
 */
@Alias("class")
public class SClass {
    private String cNo;
    private String cName;
    private Integer grade;

    public SClass() {
    }

    public SClass(String cNo, String cName, Integer grade) {
        this.cNo = cNo;
        this.cName = cName;
        this.grade = grade;
    }

    public String getcNo() {
        return cNo;
    }

    public void setcNo(String cNo) {
        this.cNo = cNo;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "SClass{" +
                "cNo='" + cNo + '\'' +
                ", cName='" + cName + '\'' +
                ", grade=" + grade +
                '}';
    }
}
