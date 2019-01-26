package top.flytop.studentsign.pojo;

import org.apache.ibatis.type.Alias;

/**
 * @ClassName User
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2018/12/29 17:26
 * @Version 1.0
 */
@Alias("user")
public class User {
    private Integer id;
    private String username;
    private String pwd;
    private Integer typeId;

    public User() {
    }

    public User(Integer id, String username, String pwd, Integer typeId) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
        this.typeId = typeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", typeId=" + typeId +
                '}';
    }
}
