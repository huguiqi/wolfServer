package com.wolf.bean;


import com.wolf.common.utils.JaxbUtils;
import com.wolf.dto.auth.AuthDto;
import com.wolf.dto.bean.BeanDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BeanTest {

    @Test
    public void testBean(){

        BeanDto beanDto = new BeanDto();
        List<AuthDto> list = new ArrayList<AuthDto>();
        AuthDto authDto = new AuthDto();
        authDto.setName("xiaohei");
        authDto.setCode("110");
        authDto.setDesc("haha");
        authDto.setId(2);
        list.add(authDto);
        beanDto.putMap(list);
        System.out.println("===="+ JaxbUtils.convertToXmlString(beanDto)+"====");
    }
}
