package com.wolf.test;


import com.wolf.authentication.domain.Account;
import com.wolf.authentication.domain.guest.SystemEntity;
import com.wolf.dto.auth.AccountDto;
import com.wolf.dto.bean.user.GuestDto;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JackSonJSONTest {

    @Test
    public void  testUserListToJson(){
        SystemEntity systemEntity = new SystemEntity();

        systemEntity.setName("小黑");

        List<AccountDto> accountList = new ArrayList<AccountDto>();
        Account account = new Account();
        account.setUserName("老黑子");
        account.setPassword("xxxx");
        accountList.add(account.toDto());

        systemEntity.setAccountList(accountList);
        List<GuestDto> guestDtoList = new ArrayList<GuestDto>();
        GuestDto guestDto = new GuestDto();
        guestDto.setName("xiaohei");
        guestDtoList.add(guestDto);
        guestDtoList.add(guestDto);
        ObjectMapper om = new ObjectMapper();
        try {
            om.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String guests = om.writeValueAsString(guestDtoList);
            systemEntity.setGuests(guests);
            String jsonStr = om.writeValueAsString(systemEntity);
            System.out.println(jsonStr);
            System.out.println(systemEntity.getGuests());
            SystemEntity se = om.readValue(jsonStr,SystemEntity.class);
            System.out.println("==="+se.getGuestArray().size()+"===");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
