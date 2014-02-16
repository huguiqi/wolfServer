package com.wolf.test;

import com.wolf.authentication.domain.Account;
import com.wolf.authentication.persistent.UserMapper;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/spring/testApplicationContext.xml" })
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;
    @Test
    public void  testGetUser(){
        Account account = userMapper.getUser(1L);
       Assert.assertThat(account.getPassword(), Is.is("qiguihu"));
    }
}
