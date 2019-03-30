package top.flytop.studentsign.pojo;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

@Alias(value = "admin")
@Component("admin")
public class Admin implements Serializable {
    String username;
    String realName;
    Timestamp lastLogin;

    public Admin() {
    }

    public Admin(String username, String realName, Timestamp lastLogin) {
        this.username = username;
        this.realName = realName;
        this.lastLogin = lastLogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
