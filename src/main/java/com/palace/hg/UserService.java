package com.palace.hg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 */
public class UserService  extends Controller { 
	private static final long serialVersionUID = 1L;
    String[] colArr = "lId,strUserName,strPhone,lHeight,lWeight,lBMI,lWantWeight,lXiongWei,lShouBi,lDaTui,lXiaoTui,strJianFei,strStartTime,strEndTime,strProductName,strShuiGuo,strAoYe,strShiJiu,strBianMi,strBianMiTianShu,strGaoXueYa,strZuiGaoXueYa,strZuiDiXueYa,strTangNiaoBing,strXueTangZhi,strPinXue,strXueHongDanBai,strChangWeiYan,strZhiFangGan,strZhiFangGanNeiRong,strYunDong,strShuiZhong,strTongJing,strShangCiYueJing,strYueJingZhouQi,strWuChan,strYinShui,strMsg,dtCreateTime".split(",");
    
    public String[] getCusColArr() {
    	List<Map<String,Object>> list = Db.query("SELECT COLUMN_NAME as col from information_schema.`COLUMNS` mm where mm.TABLE_NAME=?","cus");
    	return list.toArray(new String[list.size()]);
    }
	 
	public void saveCus() {
		Map<String,Object> obj = new HashMap<String,Object>();
		for(String col : getCusColArr()){
			String val = getPara(col);
			if(StringUtils.isNotBlank(val)){
				if(col.startsWith("l")){
					long lVal = 0l;
					try{
						lVal = Long.parseLong(val);
					}catch(Exception e){
						result(-1, col);
						return ;
					}
					obj.put(col, lVal);
				}else if(col.startsWith("str")){
					obj.put(col, val);
				}
			} 
		}
		if(obj.isEmpty()){
			result(-1,getMsg("请填写数据"));
			return;
		}
		
		if(StringUtils.isBlank((String)obj.get("strUserName"))){
			result(-1,"用户名不能为空");
			return;
		}
		
		String strPhone = (String)obj.get("strPhone");
		if(StringUtils.isBlank(strPhone)){
			result(-1,"电话号码不能为空");
			return;
		}
		try {
			Long.parseLong(strPhone);
		}catch(Exception e) {
			result(-1,"电话号码错误");
			return;
		}
		strPhone = Db.queryStr("select strPhone from cus where strPhone = ? ",obj.get("strPhone"));
		if(StringUtils.isNoneBlank(strPhone)){
			update(obj);
			return ;
		}
		String sql =" insert into cus(";
		String tmpSql ="values(";
		for(Map.Entry<String,Object> ent : obj.entrySet()){
			sql+=ent.getKey()+",";
			tmpSql+="?,";
		}
		sql = sql.substring(0,sql.length()-1)+") "+tmpSql.substring(0,tmpSql.length() -1 )+") ";
		int r = Db.update(sql, obj.values().toArray(new Object[obj.values().size()]));
		if(r > 0 ){
			result(0, "保存成功");
		}else{
			result(0, "保存失败，请检查参数");
		}
	}
	private void update(Map<String, Object> map) {
		for(String col : getCusColArr()){
			if(col.equals("lId")) {
				continue;
			}
			Object val = map.get(col);
			if(val == null ) {
				if(col.startsWith("l")) {
					map.put(col, 0);
				}else if(col.startsWith("str")) {
					map.put(col, "");
				}
			}
		}
		String upSql = updateSql("cus", map)+" where strPhone = '"+MapUtils.getString(map,"strPhone").trim()+"' ";
		int r = Db.update(upSql,map.values().toArray(new Object[map.values().size()]));
		if(r > 0) {
			result(0,"保存成功");
		}else {
			result(0,"保存失败");
		}
	}

	public String updateSql(String tableName,Map<String,Object> map) {
		StringBuilder sb = new StringBuilder("update ").append(tableName).append(" set ");
		for(Map.Entry<String,Object> ent : map.entrySet()) {
			sb.append(ent.getKey()).append("=?,");
		}
		return sb.deleteCharAt(sb.length() -1).toString();
	}
	public String getMsg(String key){
		String r = Db.queryStr("select * from dict where strKey = ?",key.trim());
		if(r == null) {
			return "";
		}
		return r;
	}
	
	public  void result(int code ,String msg){
		Map<String,Object> map = new HashMap<>();
		map.put("code", code);
		map.put("msg", msg);
		renderJson(map);
	}
	
	public void getByPhone() {
		String strPhone = getPara("strPhone");
		if(StringUtils.isBlank(strPhone)) {
			result(-1, "");
			return;
		}
		String sql =" select * from cus where strPhone =? ";
		Record rr  = Db.findFirst(sql,strPhone);
		if(rr == null ) {
			result(-1, "");
			return;
		}
		Map<String,Object> map = new HashMap<>();
		map.put("code", 0);
		map.put("msg", "");
		map.put("data", rr.getColumns());
		renderJson(map);
	}
	public void getCus() {
		Map<String,Object> map = new HashMap<>();
		String strUserName = getPara("strUserName");
		if(StringUtils.isNotBlank(strUserName)) {
			map.put("strUserName", strUserName);
		}
		String strPhone = getPara("strPhone");
		if(StringUtils.isNotBlank(strPhone)) {
			map.put("strPhone", strPhone);
		}
		renderJson(getPage("hg","cus",map));
	}
	
    public static Map<String,Object> getPage(String strDBName,String strTableName,Map<String, Object> params){
    	StringBuilder sb =new StringBuilder(" where  1=1 ");
    	String strLimit = getLimit(params);
    	LinkedList<Object> list = new LinkedList();
    	assebleParam(params, sb, list,null);
    	Map<String,Object> map = new HashMap<String,Object>();
		String sql = "select count(1) total from "+strDBName+"."+strTableName+" "+sb.toString();
		map.put("total", Db.queryInt(sql,list.toArray(new Object[list.size()])));
		sb.append(strLimit);
		sql = "select * from  "+strDBName+"."+strTableName+" "+sb.toString();
		List<Record> rList = Db.find(sql,list.toArray(new Object[list.size()]));
		List<Map<String,Object>> rr = new ArrayList<>();
		for(Record r : rList) {
			rr.add(r.getColumns());
		}
		map.put("rows",rr);
    	return map;
    }
    
    public static String getLimit(Map<String,Object> map) {
    	StringBuilder sb =new StringBuilder("  ");
    	
    	int rows = 300;
    	if(map.containsKey("rows")) {
    		rows = MapUtils.getIntValue(map, "rows");
    	}
    	
    	int page = 1;
    	if(map.containsKey("page")) {
    		page = MapUtils.getIntValue(map, "page");
    	} 
    	
    	sb.append(" limit ").append((page-1)*rows).append(",").append(rows);
    	map.remove("rows");
    	map.remove("page");
    	return sb.toString();
    }
    
    public static void assebleParam(Map<String,Object> params,StringBuilder sb,List<Object> list,String joinKey) {
    	for(Map.Entry<String, Object> entry : params.entrySet()) {
    		if(entry.getKey().startsWith("str")) {
    			if(StringUtils.isNoneBlank((String)entry.getValue())) {
	    			if(joinKey != null) {
	    				sb.append(" and ").append(joinKey).append(".");
	    			}else {
	    				sb.append(" and ");
	    			}
	    			String key = entry.getKey();
	    			if(key.endsWith("_like")) {
	    				key = key.split("_")[0];
	    				sb.append(key).append(" like ? ");
	    				list.add("%"+entry.getValue()+"%");
	    			}else {
	    				sb.append(entry.getKey()).append(" =? ");
	    				list.add(entry.getValue());
	    			}
		    		
    			}
    		}else {
    			if(entry.getKey().startsWith("l")) {
    				if(MapUtils.getLongValue(params,entry.getKey())>0) {
    					if(joinKey !=null) {
            				sb.append(" and ").append(joinKey).append(".");
            			}else {
            				sb.append(" and ");
            			}
    					sb.append(entry.getKey()).append(" =? ");
    					list.add(entry.getValue());
    				}
    			}
    			if(entry.getKey().startsWith("i")) {
    				if(MapUtils.getIntValue(params,entry.getKey())>0) {
    					if(joinKey !=null) {
            				sb.append(" and ").append(joinKey).append(".");
            			}else {
            				sb.append(" and ");
            			}
    					sb.append(entry.getKey()).append(" =? ");
    					list.add(entry.getValue());
    				}
    			}
	    		
    		}
    	}
    }
}
