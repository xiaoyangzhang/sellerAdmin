package com.yimayhd.sellerAdmin.controller.loginout.vo;

import java.io.Serializable;

/**
 * Created by root on 15-11-12.
 */
public class LoginoutVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private String verifyCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LoginoutVO{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", verifyCode='").append(verifyCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
