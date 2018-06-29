package com.jason.hello;


import org.apache.log4j.Logger;

/**
 * @Author : jasonzii @Author
 * @Description :
 * @CreateDate : 18.6.14  13:44
 */
public class LogTest {

    private static Logger logger = Logger.getLogger(LogTest.class);

    public static void main(String [] args){

        //BasicConfigurator.configure(); //自动快速地使用缺省Log4j环境。

        logger.info("aaaa");

        logger.debug("bbbb");

        logger.error("cccc");

    }

}
