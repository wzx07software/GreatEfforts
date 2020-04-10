package com.yitongmed.multitenant.business.service.impl;

import com.yitongmed.multitenant.business.service.HelloService;
import com.yitongmed.multitenant.common.mybatis.mapper.RecordMainExtMapper;
import com.yitongmed.multitenant.common.mybatis.model.RecordMainExtCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Autowired
    private RecordMainExtMapper recordMainExtMapper;

    @Override
    @Transactional
    public String sayHello() {
        RecordMainExtCriteria recordMainExtCriteria = new RecordMainExtCriteria();
        recordMainExtCriteria.createCriteria().andDelFlgEqualTo(0);
        Long count = recordMainExtMapper.countByExample(recordMainExtCriteria);
        log.info("Hello!");
        return "Hello! I'm Willem. There is " + count + " records";
    }

    @Override
    @Transactional
    public String sayBye() {
        RecordMainExtCriteria recordMainExtCriteria = new RecordMainExtCriteria();
        recordMainExtCriteria.createCriteria().andDelFlgEqualTo(0);
        Long count = recordMainExtMapper.countByExample(recordMainExtCriteria);
        log.info("Byebye!");
        return "Byebye! I'm Willem. There is " + count + " records";
    }

    @Override
    @Transactional
    public void deleteOneRecord() {
        //证明事物控制是准确的
//        RecordMainExtCriteria recordMainExtCriteria = new RecordMainExtCriteria();
//        recordMainExtCriteria.createCriteria().andDelFlgEqualTo(0);
//        Long id = recordMainExtMapper.selectByExample(recordMainExtCriteria).get(0).getId();
//        recordMainExtMapper.deleteByPrimaryKey(id);
//        recordMainExtMapper.deleteByPrimaryKey(id);
//        recordMainExtMapper.deleteByPrimaryKey(id);
//        recordMainExtMapper.deleteByPrimaryKey(id);
//        recordMainExtMapper.deleteByPrimaryKey(id);
//        recordMainExtMapper.deleteByPrimaryKey(id);
//        throw new RuntimeException("主动抛出");
    }
}
