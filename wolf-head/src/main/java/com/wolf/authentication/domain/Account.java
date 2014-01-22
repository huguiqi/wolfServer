package com.wolf.authentication.domain;

import com.wolf.dto.auth.AccountDto;
import org.springframework.beans.BeanUtils;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

public class Account implements Serializable {

    private Long id;
    private String userName;
    private String password;
    private String userCode;
    private Integer access;

    private RoleModel role;

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

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public AccountDto toDto() {
        AccountDto dto = new AccountDto();
        String[] ignors = new String[]{"role"};
        BeanUtils.copyProperties(this, dto, ignors);
        if (role != null)
            dto.setRoleDto(role.toDto());
        return dto;
    }
}
