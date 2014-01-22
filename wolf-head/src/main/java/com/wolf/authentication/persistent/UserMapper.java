package com.wolf.authentication.persistent;


import com.wolf.authentication.domain.Account;

import java.util.List;

public interface UserMapper {
       Account getUserByUserName(String userName);

    List<Account> getUserList();
}
