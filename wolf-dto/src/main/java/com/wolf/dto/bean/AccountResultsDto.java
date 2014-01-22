package com.wolf.dto.bean;

import com.wolf.dto.auth.AccountDto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class AccountResultsDto {
    
    private List<AccountDto> accountDtos;

    public List<AccountDto> getAccountDtos() {
        return accountDtos;
    }

    public void setAccountDtos(List<AccountDto> accountDtos) {
        this.accountDtos = accountDtos;
    }
}
