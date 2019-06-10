package com.agile.websocket.business.service;


import com.agile.websocket.business.dto.UserDTO;
import com.agile.websocket.business.entity.SingleChat;
import com.agile.websocket.business.entity.User;
import com.agile.websocket.business.mapper.SingleChatMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 单聊表 服务实现类
 * </p>
 *
 * @author liuyi
 * @since 2019-05-31
 */
@Service
public class SingleChatServiceImpl extends ServiceImpl<SingleChatMapper, SingleChat> {


    @Resource
    private SingleChatMapper singleChatMapper;
    /**
     * 根据用户id获取单聊记录
     * @param userId
     * @return
     */
    public List<UserDTO> getSingleListByUserId(Long userId) {
        return singleChatMapper.getListByUserId(userId);
    }

    /**
     * 根据对方用户id获取单聊记录
     * @param userId
     * @return
     */
    public List<UserDTO> getSingleListByOtherUserId(Long userId) {
        return singleChatMapper.getListByOtherUserId(userId);
    }

    /**
     * 获取根据用户id获取好友记录
     * @param user
     */
    public void setUserFriendList(User user) {
        List<UserDTO> userIdList = getSingleListByUserId(user.getId());
        List<UserDTO> otherUserIdList = getSingleListByOtherUserId(user.getId());
        Set<UserDTO> set = new HashSet<>();
        set.addAll(userIdList);
        set.addAll(otherUserIdList);
        List<UserDTO> resultList = new ArrayList<>();
        resultList.addAll(set);
         user.setFriendList(resultList);
    }


}
