package com.jason.hello;

/**
 * @Author : jasonzii @Author
 * @Description :
 * @CreateDate : 18.6.14  14:04
 */

import org.apache.log4j.Logger;
import org.quartz.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 任务执行类  实现  Job接口
 * 该接口只有一个方法
 */
public class FirstJob implements Job {

    private static Logger logger = Logger.getLogger(FirstJob.class);

    ////开发者实现该接口定义需要执行的任务。JobExecutionContext类提供调度上下文的各种信息
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        //通过上下文获取
        JobKey jobKey = context.getJobDetail().getKey();

        //往JobDataMap放数据
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String jobSays = dataMap.getString("jobSays");
        float myFloatValue = dataMap.getFloat("myFloatValue");
        String triggerSays = dataMap.getString("trigger_key");


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now = LocalDateTime.now().format(formatter);
        System.out.println(jobKey + "在" + now + "时，输出了：hi, quartz!!!");
        // jobKey就是前面指定的 group_1.testJob_1 组名和名字

        //do more这里可以执行其他需要执行的任务
        logger.info(jobKey + "在" + now + "时，输出了：hi, quartz!!!");




    }




}
