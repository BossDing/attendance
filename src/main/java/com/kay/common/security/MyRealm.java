package com.kay.common.security;

import com.kay.user.entity.User;
import com.kay.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by kay on 2018/3/7.
 * 实现自定义的Realm
 */
public class MyRealm extends AuthorizingRealm{

    @Autowired
    private UserService userService;


    /***
     * 授权规则
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username =(String) principalCollection.getPrimaryPrincipal();
        User user=userService.findUserByUserName(username);
        return null;
    }

    /**
     * 认证/登录验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToke = (UsernamePasswordToken)authenticationToken;
        String username =  usernamePasswordToke.getUsername();
        User user = userService.findUserByUserName(username);
        if(user==null){
            return null;
        }else {
            AuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
            SecurityUtils.getSubject().getSession().setAttribute("userinfo",user);
            return info;
        }
    }
}
