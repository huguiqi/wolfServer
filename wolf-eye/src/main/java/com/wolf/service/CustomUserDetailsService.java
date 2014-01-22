package com.wolf.service;

import com.wolf.dto.auth.AccountDto;
import com.wolf.dto.auth.AuthDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Account: sam.hu
 * Date: 11-8-28
 * Time: 下午10:27
 * 一个自定义的service用来和数据库进行操作.
 * 即以后我们要通过数据库保存权限.
 * 则需要我们继承UserDetailsService
 */

public class CustomUserDetailsService implements UserDetailsService {

    protected static Logger logger = Logger.getLogger("service");

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private Md5PasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
        UserDetails user = null;
        AccountDto account = null;
        try {
            account = loginUserService.getUserByUserName(userName);
            user = new User(account.getUserName(),
                    getMd5EncodePassword(account.getPassword().toLowerCase()),
                    true, true, true, true, getAuthorities(account));
        } catch (Exception e) {
            logger.error("Error in retrieving user", e);
            e.printStackTrace();
            throw new UsernameNotFoundException("Error in retrieving user");
        }
        return user;
    }

    /**
     * 获得访问角色权限
     *
     * @param userAccount
     * @return
     */
    public Collection<GrantedAuthority> getAuthorities(AccountDto userAccount) {

        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        if (userAccount.getRoleDto().getAuthList() != null && userAccount.getRoleDto().getAuthList().size() != 0)
            for (AuthDto auth : userAccount.getRoleDto().getAuthList()) {
                authList.add(new GrantedAuthorityImpl(auth.getName()));
            }
        return authList;
    }

    /** */
    /**
     * 利用MD5进行加密
     *
     * @param str 待加密的字符串
     * @return 加密后的字符串
     * @throws NoSuchAlgorithmException     没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException
     */
    public String encoderByMd5(String str) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

    private String getMd5EncodePassword(String password) {
        String result = passwordEncoder.encodePassword(password, null);
        return result;
    }
}
