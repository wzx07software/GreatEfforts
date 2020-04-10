package com.yitongmed.multitenant.business.service;

import org.springframework.transaction.annotation.Transactional;

public interface HelloService {
    String sayHello();

    String sayBye();

    @Transactional
    void deleteOneRecord();
}
