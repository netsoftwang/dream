package com.palace.hg;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;

/**
 */
public class LoginService  extends Controller { 
	 
    public void login() throws Exception {
    	String strUserName = getPara("strUserName");
    	String strPassword = getPara("strPassword");
    	if(StringUtils.isBlank(strUserName) || StringUtils.isBlank(strPassword)) {
    		render("login.html");
    		return;
    	}
    	
    	String sql = " select strPassword from user where strUserName = ? ";
    	String strPass = Db.queryStr(sql,strUserName);
    	if(StringUtils.isBlank(strPass)) {
    		render("login.html");
    		return;
    	}
    	if(AESUtil.encrypt(strPassword).equals(strPass)) {
    		render("static/cus.html");
    	}else {
    		render("login.html");
    	}
    }
    
     
}