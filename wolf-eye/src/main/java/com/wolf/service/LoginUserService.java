package com.wolf.service;

import com.wolf.common.utils.RestClient;
import com.wolf.dto.auth.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service(value = "loginUserService")
public class LoginUserService {

    @Value("${getAccount.authentication.url}")
    private String accountAuthcationUrl;

    @Autowired
    private RestClient restClient;

    public AccountDto getUserByUserName(String userName) {
       AccountDto account =  restClient.get(accountAuthcationUrl+"/"+userName,AccountDto.class);
       return account;
    }
}
