package com.agile.websocket.business.service;


import com.agile.websocket.business.entity.User;
import com.agile.websocket.business.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author liuyi
 * @since 2019-05-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> {


    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    public User getOneByUserName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return super.getOne(queryWrapper);
    }

    /**
     * 获取所有的用户id
     * @return
     */
    public List<Long> getAllIds() {
        List<User> list = super.list();
        List<Long> resultList = new ArrayList<>();
        for (User user: list) {
            resultList.add(user.getId());
        }
        return resultList;
    }

}
