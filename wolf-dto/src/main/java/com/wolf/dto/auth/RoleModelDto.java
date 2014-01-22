package com.wolf.dto.auth;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement
public class RoleModelDto {
    private Integer id;
    private String code;
    private String name;
    private String desc;

    private List<AuthDto> authList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<AuthDto> getAuthList() {
        return authList;
    }

    public void setAuthList(List<AuthDto> authList) {
        this.authList = authList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
