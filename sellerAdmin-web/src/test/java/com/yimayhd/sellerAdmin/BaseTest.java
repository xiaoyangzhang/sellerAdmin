package com.yimayhd.sellerAdmin;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author wzf
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context.xml")
//@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/application.xml")
public class BaseTest {
    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void printResult(Object result, String method) {
        String json = JSON.toJSONString(result);
        System.out.println("**************************   " + method + " start");
        System.out.println(json);
        System.out.println("**************************   " + method + " end/n/n");
    }
}

