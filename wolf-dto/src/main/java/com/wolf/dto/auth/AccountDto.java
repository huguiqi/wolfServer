package com.wolf.dto.auth;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class AccountDto implements Serializable{

    private Long id;
    private String userName;
    private String password;
    private String userCode;
    private Integer access;

    private RoleModelDto roleDto;

    private List<AuthDto> authList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public RoleModelDto getRoleDto() {
        return roleDto;
    }

    public void setRoleDto(RoleModelDto roleDto) {
        this.roleDto = roleDto;
    }

    public List<AuthDto> getAuthList() {
        return authList;
    }

    public void setAuthList(List<AuthDto> authList) {
        this.authList = authList;
    }
}
