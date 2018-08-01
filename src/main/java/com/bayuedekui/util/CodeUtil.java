package com.bayuedekui.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * y验证验证码是否正确
 */
public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request){
        //获取真实的验证码
        String verifyCodeExpected=(String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //获取输入的验证码
        String verifyCodeInput=HttpServletRequestUtil.getString(request,"verifyCodeInput");
        
        if(verifyCodeExpected==null||!verifyCodeExpected.toLowerCase().equals(verifyCodeInput.toLowerCase())){
            return false;
        }
            return true;
        
    } 
}
