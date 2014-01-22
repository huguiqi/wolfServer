package com.wolf.authentication.domain;


import com.wolf.dto.auth.AuthDto;
import org.springframework.beans.BeanUtils;

public class Auth {
    private Integer id;
    private String code;
    private String name;
    private String desc;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AuthDto toDto() {
        AuthDto dto = new AuthDto();
        String [] ignors = new String []{"authList"};
        BeanUtils.copyProperties(this, dto);
        return dto;
    }
}
