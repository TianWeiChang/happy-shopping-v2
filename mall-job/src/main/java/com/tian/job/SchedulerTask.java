package com.tian.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月11日 15:29
 */
@Component
public class SchedulerTask {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerTask.class);

    /**
     * Scheduled(fixedRate = 6000) ：上一次开始执行时间点之后6秒再执行
     * Scheduled(fixedDelay = 6000) ：上一次执行完毕时间点之后6秒再执行
     * Scheduled(initialDelay=1000, fixedRate=6000) ：第一次延迟1秒后执行，之后按fixedRate的规则每6秒执行一次
     * Scheduled(cron=""): 详见cron表达式 http://www.pppet.net/
     */
    /*@Scheduled(fixedRate = 5000)
    public void scheduled1() {
        logger.info("=====>>>>>使用fixedRate执行定时任务");
    }

    @Scheduled(fixedDelay = 10000)
    public void scheduled2() {
        logger.info("=====>>>>>使用fixedDelay执行定时任务");
    }*/
    @Scheduled(cron = "0/10 * * * * ? ")
    private void scheduled3() {
        logger.info("使用cron执行定时任务:每10秒执行一次");
    }
}
