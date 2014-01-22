package com.wolf.dto.bean;

import com.wolf.dto.auth.AuthDto;
import com.wolf.dto.auth.RoleModelDto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
public class BeanDto implements Serializable{

    private Map<String,List<AuthDto>> map = new HashMap<String,List<AuthDto>>();

    @XmlElement
    public List<AuthDto> getList(){
       return (List<AuthDto>)map.get("HOTEL");
    }

    public void putMap(List<AuthDto> list){
        map.put("HOTEL",list);
    }

}
