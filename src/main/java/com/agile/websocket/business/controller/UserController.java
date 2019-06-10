package com.agile.websocket.business.controller;


import com.agile.websocket.business.base.BaseController;
import com.agile.websocket.business.entity.User;
import com.agile.websocket.business.service.GroupChatServiceImpl;
import com.agile.websocket.business.service.SingleChatServiceImpl;
import com.agile.websocket.business.service.UserServiceImpl;
import com.agile.websocket.common.util.WebResult;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author liuyi
 * @since 2019-05-31
 */
@RestController
@RequestMapping("/user/")
public class UserController extends BaseController {

    @Resource
    private UserServiceImpl userService;
    @Resource
    private SingleChatServiceImpl singleChatService;
    @Resource
    private GroupChatServiceImpl groupChatService;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("login")
    public WebResult login(String username, String password) {
        if (StringUtils.isBlank(username)) {
            return WebResult.error("用户名不能为空");
        }
        if (StringUtils.isBlank(password)) {
            return WebResult.error("密码不能为空");
        }
        User user = userService.getOneByUserName(username);
        if (user == null || !user.getPassword().equals(password)) {
            return WebResult.error("账号或密码不正确");
        }
        return new WebResult().put("userId", user.getId());
    }

    /**
     * 获取登陆用户信息
     * @param userId
     * @return
     */
    @RequestMapping("info")
    public WebResult getInfo(String userId) {
        if (StringUtils.isBlank(userId)) {
            return WebResult.error("用户不能为空");
        }
        //获取用户信息
        User user = userService.getById((Serializable)userId);
        if (user == null) {
            return WebResult.error("用户不存在");
        }
        //获取好友列表
        singleChatService.setUserFriendList(user);
        //获取群组列表
        groupChatService.setGroupList(user);
        return new WebResult().put("data", user);
    }

    /**
     * 获取所有用户id
     * @return
     */
    @RequestMapping("allUserIds")
    public WebResult getAllUser() {
        List<Long> list = userService.getAllIds();
        return new WebResult().put("data", list);
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("logout")
    public WebResult logout() {
        return new WebResult();
    }



}
