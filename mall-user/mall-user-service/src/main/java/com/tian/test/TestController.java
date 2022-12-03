package com.tian.test;

import com.tian.entity.AdminUser;
import com.tian.service.MallAdminService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @DubboReference(version = "1.0.0", check = false)
    private MallAdminService mallAdminService;

    @RequestMapping("/test")
    public void test() {
        AdminUser adminUser = mallAdminService.getUserDetailById(1);
        System.out.println(adminUser);
    }
}
