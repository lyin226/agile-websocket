package com.agile.websocket;

import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liuyi
 * @date 2019/5/27
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = {"com.agile.websocket.*"})
public abstract class BaseTest {
    

}
