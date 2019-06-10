package com.agile.websocket;

import com.agile.websocket.business.entity.User;
import com.agile.websocket.business.mapper.SingleChatMapper;
import com.agile.websocket.business.service.UserServiceImpl;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author liuyi
 * @date 2019/6/3
 */
public class ChatTest extends BaseTest{


    @Resource
    private SingleChatMapper singleChatMapper;
    @Resource
    private UserServiceImpl userService;

    @Test
    public void testChat() {
        String userId = "11";
        User user = userService.getById((Serializable)userId);
    }

    @Test
    public void testSingleChat() {
        Long userId = 11L;
        singleChatMapper.getListByUserId(userId);
    }
}
