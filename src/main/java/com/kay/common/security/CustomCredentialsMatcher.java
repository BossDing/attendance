package com.kay.common.security;

import com.kay.common.utils.MD5Uitils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kay on 2018/3/7.
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        try {
            UsernamePasswordToken usertoken = (UsernamePasswordToken) token;
            String password = String.valueOf(usertoken.getPassword());
            Object tokenCredentials = MD5Uitils.encryptPassword(password);
            Object accountCredentials =getCredentials(info);
            return equals(tokenCredentials,accountCredentials);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
