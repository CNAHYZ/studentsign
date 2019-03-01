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
    private String username;
    private String pwd;
    private String salt;
    private Integer type;

    public User() {
    }

    public User(String username, String pwd, String salt, Integer type) {
        this.username = username;
        this.pwd = pwd;
        this.salt = salt;
        this.type = type;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
                "username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", salt='" + salt + '\'' +
                ", type=" + type +
                '}';
    }
}
