package top.flytop.studentsign.pojo;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Alias(value = "admin")
@Component("admin")
public class Admin implements Serializable {
    Integer id;
    String username;
    Date lastLogin;

    public Admin() {
    }

    public Admin(Integer id, String username, Date lastLogin) {
        this.id = id;
        this.username = username;
        this.lastLogin = lastLogin;
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

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
