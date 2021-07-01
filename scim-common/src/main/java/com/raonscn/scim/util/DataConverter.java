package com.raonscn.scim.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataConverter {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	static public <T> T convert(Object obj, Class<T> type) {
		T cvt_obj = null;
		try {
			cvt_obj = type.newInstance();
			
			
			
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return cvt_obj;
	}
	static public String toString(Object obj) {
		if(obj == null) return null;
		
		if (obj instanceof Date) {
			Date date_obj = (Date) obj;
			return sdf.format(date_obj);
		}else if(obj instanceof Timestamp) {
			Timestamp time_obj = (Timestamp) obj;
			return time_obj.toString();
		}else {
			return obj.toString();
		}
	}
	
	static public Integer toInteger(String str) {
		if(str == null) return new Integer(0);
		try {
			return Integer.parseInt(str);
		}catch (NumberFormatException e) {
			return new Integer(0);
		}
	}
	
	static public Date toDate(String str) {
		if(str == null) return new Date();
		try {
			return sdf.parse(str);
		}catch (ParseException  e) {
			return new Date();
		}
	}
}
