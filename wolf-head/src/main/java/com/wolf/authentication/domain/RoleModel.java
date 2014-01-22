package com.wolf.authentication.domain;


import com.wolf.dto.auth.AuthDto;
import com.wolf.dto.auth.RoleModelDto;
import org.springframework.beans.BeanUtils;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class RoleModel {
    private Integer id;
    private String code;
    private String name;
    private String desc;

    private List<Auth> authList;

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

    public List<Auth> getAuthList() {
        return authList;
    }

    public void setAuthList(List<Auth> authList) {
        this.authList = authList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RoleModelDto toDto() {
        RoleModelDto dto = new RoleModelDto();
        String [] ignors = new String []{"authList"};
        BeanUtils.copyProperties(this, dto, ignors);
        if (authList != null && authList.size() != 0) {
            List<AuthDto> authDtos = new ArrayList<AuthDto>();
            for (Auth auth : authList) {
                AuthDto authDto = new AuthDto();
                authDto = auth.toDto();
                authDtos.add(authDto);
            }
            dto.setAuthList(authDtos);
        }
        return dto;
    }
}
