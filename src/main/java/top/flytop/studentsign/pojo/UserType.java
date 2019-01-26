package top.flytop.studentsign.pojo;

import org.apache.ibatis.type.Alias;

@Alias(value = "userType")
public class UserType {
    Integer id;
    String typeId;
    String typeName;

    public UserType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return typeId;
    }

    public UserType(Integer id, String type, String typeName) {
        this.id = id;
        this.typeId = type;
        this.typeName = typeName;
    }

    public void setType(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "id=" + id +
                ", type='" + typeId + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
