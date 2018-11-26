package com.palace.hg;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;


public class Bootstrap extends JFinalConfig {
	private static Logger log = LoggerFactory.getLogger(Bootstrap.class);
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("config.properties");
		me.setDevMode(PropKit.getBoolean("devMode", false));
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/cus", UserService.class,"/");
		me.add("/login", LoginService.class,"/");
		me.add("/", Index.class);
		me.setBaseViewPath("/");
		
	}
	
	public void configEngine(Engine me) {
		//me.setBaseTemplatePath("/");
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置 druid 数据库连接池插件
		DruidPlugin druidPlugin = createDruidPlugin();
		me.add(druidPlugin);
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.setShowSql(true);
		me.add(arp);
	}
	
	public static DruidPlugin createDruidPlugin() {
		return new DruidPlugin(
				PropKit.get("write.url").trim(),
				PropKit.get("write.username").trim(), 
				PropKit.get("write.password").trim(),
				PropKit.get("write.driver-class-name").trim());
	}
	
	@Override  
    public void afterJFinalStart() {  
  
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		 
	}
}
