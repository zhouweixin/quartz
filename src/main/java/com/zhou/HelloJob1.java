package com.zhou;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author: zhouweixin
 * @Description:
 * @Date: Created in 下午7:59:33 2018年6月16日
 */
public class HelloJob1 implements Job {
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("【打印】" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " Hello world!");
	}
}
