package com.tian.service;

import com.tian.dao.InitUserCreditTmpMapper;
import com.tian.dto.UserCreditDto;
import com.tian.entity.InitUserCreditTmp;
import com.tian.utils.Result;
import com.tian.utils.ResultGenerator;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月11日 17:41
 * <p>
 * 有定时任务统一触发
 */
@DubboService(version = "1.0.0")
public class InitUserCreditTmpDubboServiceImpl implements InitUserCreditTmpDubboService {
    private Logger logger = LogManager.getLogger(InitUserCreditTmpDubboServiceImpl.class);

    @DubboReference(version = "1.0.0", check = false)
    private UserCreditDubboService userCreditDubboService;

    @Resource
    private InitUserCreditTmpMapper initUserCreditTmpMapper;


    private final static int PAGE_SIZE = 20;

    @Override
    public void initUserCredit() {
        logger.info("定时任务初始化账户开始");
        int count = initUserCreditTmpMapper.count();
        if (count == 0) {
            logger.info("没有续约初始化的积分账户");
            return;
        }

        if (count <= PAGE_SIZE) {
            initUserCreditTmpMapper.selectPage(0, count);
        }

        int length = count / PAGE_SIZE;
        int start = 0;

        for (int i = 0; i <= length; i++) {
            List<InitUserCreditTmp> initUserCreditTmps = initUserCreditTmpMapper.selectPage(start, PAGE_SIZE);
            for (InitUserCreditTmp initUserCreditTmp : initUserCreditTmps) {
                UserCreditDto userCreditDto = new UserCreditDto();
                userCreditDto.setUserId(initUserCreditTmp.getUserId());
                Result<String> result = userCreditDubboService.initUserCredit(userCreditDto);
                if (ResultGenerator.RESULT_CODE_SUCCESS == result.getResultCode()) {
                    initUserCreditTmp.setStatus(1);
                    initUserCreditTmpMapper.updateByPrimaryKey(initUserCreditTmp);
                }
            }
            start = start + PAGE_SIZE;
        }
    }

    @Override
    public Result addInitUserCredit(Long userId) {
        InitUserCreditTmp initUserCreditTmp = new InitUserCreditTmp();
        initUserCreditTmp.setStatus(0);
        initUserCreditTmp.setUserId(userId);
        int flag = initUserCreditTmpMapper.insert(initUserCreditTmp);
        if (flag == 1) {
            return ResultGenerator.genSuccessResult("入库成功");
        }
        return ResultGenerator.genFailResult("入库失败");
    }
}
