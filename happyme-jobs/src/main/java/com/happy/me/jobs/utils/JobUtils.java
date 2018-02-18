package com.happy.me.jobs.utils;

public class JobUtils {

	public static String formCronExpression(String time){
		if(time != null){
			String hour = time.substring(0,time.indexOf(":"));
			String minutes = time.substring(time.indexOf(":")+1);
			String cronExpression = "0 " + minutes + " " + hour + " * * ?";
			return cronExpression;
		}
		return null;
	}

}
