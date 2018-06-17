package com.zhou;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

/**
 * @Author: zhouweixin
 * @Description:
 * @Date: Created in 下午7:59:33 2018年6月16日
 */
public class HelloJob3 implements Job {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 打印时间
		System.out.println("【打印】" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

		// 打印任务信息
		JobKey jobKey = context.getJobDetail().getKey();
		System.out.println(String.format("name = %s, group = %s", jobKey.getName(), jobKey.getGroup()));

		// 打印触发器信息
		TriggerKey triggerKey = context.getTrigger().getKey();
		System.out.println(String.format("name = %s, group = %s", triggerKey.getName(), triggerKey.getGroup()));

		System.out.println(String.format("message = %s", message));

	}
}
