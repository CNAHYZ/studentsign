package top.flytop.studentsign.pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * @ClassName User
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2018/12/29 17:26
 * @Version 1.0
 */
@Alias("user")
public class User implements Serializable {
    private Integer id;
    private String username;
    private String pwd;
    private String salt;
    private Integer typeId;

    public User() {
    }

    public User(Integer id, String username, String pwd, String salt, Integer typeId) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
        this.salt = salt;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", salt='" + salt + '\'' +
                ", typeId=" + typeId +
                '}';
    }
}
