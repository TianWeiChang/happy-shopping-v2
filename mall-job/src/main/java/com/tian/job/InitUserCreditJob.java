package com.tian.job;

import com.tian.service.InitUserCreditTmpDubboService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月11日 17:36
 * 定时任务扫描  用户积分初始化临时表
 */
@Component
public class InitUserCreditJob {
    private static final Logger logger = LoggerFactory.getLogger(InitUserCreditJob.class);

    @DubboReference(version = "1.0.0", check = false)
    private InitUserCreditTmpDubboService initUserCreditTmpDubboService;

    @Scheduled(cron = "0/10 * * * * ? ")
    private void scheduled3() {
        logger.info("使用cron执行定时任务:每10秒执行一次");
        initUserCreditTmpDubboService.initUserCredit();
    }
}
