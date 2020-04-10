package com.yitongmed.multitenant.business.controller;

import com.yitongmed.multitenant.business.service.HelloService;
import com.yitongmed.multitenant.common.config.DynamicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bye")
public class ByeController {

    @Autowired
    private HelloService helloService;

    @GetMapping("")
    public String hello() {
        DynamicDataSource.setDataSourceKey("tenant1");
        return helloService.sayBye();
    }

    @GetMapping("/d")
    public String delete() {
        DynamicDataSource.setDataSourceKey("tenant1");
        helloService.deleteOneRecord();
        return "";
    }
}
