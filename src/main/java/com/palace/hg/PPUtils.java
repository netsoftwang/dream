package com.palace.hg;

import java.io.IOException;
import java.util.Properties;

public class PPUtils  {

	static Properties pp = new Properties();
    static {
    	try {
			pp.load(PPUtils.class.getResourceAsStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public static String getValue(String key) {
    	return pp.getProperty(key);
    }
 
}
