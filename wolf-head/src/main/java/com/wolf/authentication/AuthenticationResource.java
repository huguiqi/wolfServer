package com.wolf.authentication;


import com.wolf.authentication.domain.Account;
import com.wolf.authentication.domain.AuthenticationRepository;
import com.wolf.dto.auth.AccountDto;
import com.wolf.dto.bean.AccountResultsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Path("/head")
@Component("authenticationResource")
public class AuthenticationResource {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @GET
    @Path("/{userName}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response getAuthenticationAccount(@PathParam("userName")String userName){
          Account account = authenticationRepository.getUserByUserName(userName);
         return Response.ok(account.toDto()).build();
    }


    @GET
    @Path("/getAccount")
    @Produces(MediaType.APPLICATION_XML)
    public Response getAccounts() {
        List<Account> accountList = new ArrayList<Account>();
        accountList = authenticationRepository.getUserList();
        List<AccountDto> resultDtos = new ArrayList<AccountDto>();
        for (Iterator<Account> iterator = accountList.iterator(); iterator.hasNext(); ) {
            Account next = iterator.next();
            resultDtos.add(next.toDto());
        }
        AccountResultsDto accountResultsDto = new AccountResultsDto();
        accountResultsDto.setAccountDtos(resultDtos);
        return Response.ok(accountResultsDto).build();
    }
}
