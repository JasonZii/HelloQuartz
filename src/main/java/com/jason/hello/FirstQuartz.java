package com.jason.hello;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;



/**
 * @Author : jasonzii @Author
 * @Description :
 * @CreateDate : 18.6.14  14:14
 */
public class FirstQuartz {

    private static Logger logger = Logger.getLogger(FirstQuartz.class);

    //创建并开启定时任务
    public void run() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        /**
         * 调用start()方法后, scheduler里有活跃的线程
         * 这个应用在调用scheduler.shutdown()后才会停止
         */
        scheduler.start();

        //定义作业并将其与我们的 FirstJob 类联系起来
        JobDetail jobDetail = JobBuilder.newJob(FirstJob.class)
                .withIdentity("FirstJob_1", "FirstGroup_1")
                //在Job执行期间从JobDataMap里获取数据
                .usingJobData("jobSays", "Hello World!")
                .usingJobData("myFloatValue", 3.141f)
                .build();

        //触发作业立即运行, 然后每40秒重复一次
        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("FirstTrigger_1", "FirstGroup_1")
                .usingJobData("trigger_key", "每40秒执行一次")
                .startNow()
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(40) //每40秒执行一次
                                // .withRepeatCount(10)       //循环10次
                                .repeatForever()           //永久开启
                )
                .build();

        //告诉quartz我们的工作安排
        // 一个trigger只能关联一个job, 一个job可以被多个trigger关联
        scheduler.scheduleJob(jobDetail, trigger);

    }

    //暂停一个任务
    //一个trigger只能对应一个job，一个job可以对应对个trigger，
    //如果暂停trigger，只是暂停了这一个触发器，job作业不受影响，其他触发器还可运行
    //如果暂停job，那么整个job作业都无法运行
    public void pause() throws SchedulerException {

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        //job暂停
        //下面两种都可获得jobkey
//        JobKey jobKey = JobKey.jobKey("FirstJob_1");
        JobKey jobKey = JobKey.jobKey("FirstJob_1", "FirstGroup_1");
        scheduler.pauseJob(jobKey);

        //Trigger暂停
        TriggerKey triggerKey = TriggerKey.triggerKey("FirstTrigger_1", "FirstGroup_1");
        scheduler.pauseTrigger(triggerKey);


        logger.info("任务停止了！！！");

    }

    //全部停止
    public void pauseAll() throws SchedulerException {

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.pauseAll();

    }

    //恢复任务
    public void resume() throws SchedulerException {

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        //job恢复
        JobKey jobKey = JobKey.jobKey("FirstJob_1", "FirstGroup_1");
        scheduler.resumeJob(jobKey);


        //Trigger恢复
        TriggerKey triggerKey = TriggerKey.triggerKey("FirstTrigger_1", "FirstGroup_1");
        scheduler.pauseTrigger(triggerKey);


    }


    public static void main(String[] args) throws Exception {
        FirstQuartz demo = new FirstQuartz();
        demo.run();
    }

}
