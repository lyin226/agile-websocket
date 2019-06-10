package com.agile.websocket;

import com.agile.websocket.business.handler.WebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liuyi
 * @date 2019/5/27
 */


@Slf4j
@SpringBootApplication
@MapperScan(basePackages = {"com.agile.websocket.*"})
@ComponentScan("com.agile.websocket.*")
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        final WebSocketHandler webSocketHandler = new WebSocketHandler();
        webSocketHandler.start();
        log.info("================agile-webSocket启动成功================");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                webSocketHandler.close();
                log.warn(">>>>>>>> jvm shutdown");
                System.exit(0);
            }
        });
    }
}
