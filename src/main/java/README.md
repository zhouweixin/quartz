# 任务调度器Squarz
---

## 1 实现Job接口

### 1.1 创建HelloJob类实现Job, 实现execute方法

```java
public class HelloJob implements Job {
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("【打印】" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "Hello world!");
	}
}
```

### 1.2 创建主程序类

```java
public class HelloScheduler {
	public static void main(String[] args) throws SchedulerException {
		// 任务详情实例
		JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob", "group1").build();
		
		// 触发器实例
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1").startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
		
		// 调试器实例
		SchedulerFactory sfact = new StdSchedulerFactory();
		Scheduler scheduler = sfact.getScheduler();
		scheduler.scheduleJob(jobDetail, trigger);
		
		System.out.println("【开始执行】" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		scheduler.start();
	}
}
```

详细解释如下:

#### 1.2.1 创建任务详情实例JobDetail

创建打印Hello world任务, 并分配到组1

```java
JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob", "group1").build();
```

#### 1.2.2 创建触发器实例Trigger

创建触发器, 设置为立即执行, 2秒一次, 一直重复

```java
Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
```

#### 1.2.3 创建调度器实例Scheduler

绑定任务和触发器

```java
SchedulerFactory sfact = new StdSchedulerFactory();
Scheduler scheduler = sfact.getScheduler();
scheduler.scheduleJob(jobDetail, trigger);
scheduler.start();
```

### 1.3 运行结果

```
【开始执行】2018-06-16 20:20:18
【打印】2018-06-16 20:20:18 Hello world!
【打印】2018-06-16 20:20:20 Hello world!
【打印】2018-06-16 20:20:22 Hello world!
【打印】2018-06-16 20:20:24 Hello world!
【打印】2018-06-16 20:20:26 Hello world!
```

## 2 利用JobDataMap传参

### 2.1 通过JobDataMap取参

HelloJob2.java

```java
public class HelloJob2 implements Job {
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 打印时间
		System.out.println("【打印】" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		// 打印任务信息
		JobKey jobKey = context.getJobDetail().getKey();
		System.out.println(String.format("name = %s, group = %s", jobKey.getName(), jobKey.getGroup()));
		
		// 打印触发器信息
		TriggerKey triggerKey = context.getTrigger().getKey();
		System.out.println(String.format("name = %s, group = %s", triggerKey.getName(), triggerKey.getGroup()));
		
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		System.out.println(String.format("message = %s", jobDataMap.get("message")));
		
		JobDataMap triggerDataMap = context.getTrigger().getJobDataMap();
		System.out.println(String.format("message = %s", triggerDataMap.get("message")));	
	}
}
```

HelloScheduler2.java

```java
public class HelloScheduler2 {
	public static void main(String[] args) throws SchedulerException {
		// 任务详情实例
		JobDetail jobDetail = JobBuilder.newJob(HelloJob2.class).withIdentity("myJob", "group1")
				.usingJobData("message", "我是jobDetail").build();

		// 触发器实例
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1")
				.usingJobData("message", "我是trigger").startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();

		// 调试器实例
		SchedulerFactory sfact = new StdSchedulerFactory();
		Scheduler scheduler = sfact.getScheduler();
		scheduler.scheduleJob(jobDetail, trigger);

		System.out.println("【开始执行】" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		scheduler.start();
	}
}
```

结果

```
【开始执行】2018-06-16 20:42:36
【打印】2018-06-16 20:42:36
name = myJob, group = group1
name = myTrigger, group = group1
message = 我是jobDetail
message = 我是trigger
```

### 2.2 通过成员变量取参

HelloJob3.java

```java
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
```

HelloScheduler3.java

```java
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

		System.out.println("【开始执行】" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		scheduler.start();
	}
}
```

结果

```
【开始执行】2018-06-16 20:51:24
【打印】2018-06-16 20:51:24
name = myJob, group = group1
name = myTrigger, group = group1
message = 我是trigger
```

特别说明: 当出现key值重复的参数时, trigger的参数会覆盖jobDetail的参数
