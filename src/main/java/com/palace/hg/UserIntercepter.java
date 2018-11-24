package com.palace.hg;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.activerecord.Db;

/**
 */
public class UserIntercepter implements Interceptor {
	   public void intercept(Invocation inv) {
		   String akVal = inv.getController().getCookie("ak");
		   if(StringUtils.isBlank(akVal)) {
			   inv.getController().render("/login.html");
			   return;
		   }else {
			   Map<String,Object> map = (Map<String, Object>) JSON.parse(akVal);
			   String val = Db.queryStr("select strPassword from user where strUserId = ?",MapUtils.getString(map, "strUserId"));
			   String pass = MapUtils.getString(map, "strPassword");
			   if(StringUtils.isBlank(val)) {
				   inv.getController().render("/login.html");
			   }else if(val.equals(pass)) {
				   inv.invoke();
			   }else {
				   inv.getController().render("/login.html");
			   }
		   }
        }
    }

