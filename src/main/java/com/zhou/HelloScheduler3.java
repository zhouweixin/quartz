package com.zhou;

import java.util.Calendar;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @Author: zhouweixin
 * @Description:
 * @Date: Created in 下午8:01:37 2018年6月16日
 */
public class HelloScheduler3 {
	public static void main(String[] args) throws SchedulerException {
		// 任务详情实例
		JobDetail jobDetail = JobBuilder.newJob(HelloJob3.class).withIdentity("myJob", "group1")
				.usingJobData("message", "我是jobDetail").build();

		// 触发器实例
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1")
				.usingJobData("message", "我是trigger").startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();

		// 调试器实例
		SchedulerFactory sfact = new StdSchedulerFactory();
		Scheduler scheduler = sfact.getScheduler();
		scheduler.scheduleJob(jobDetail, trigger);
		
		Calendar instance = Calendar.getInstance();
		System.out.println(instance.get(Calendar.DAY_OF_WEEK));
	}
}
