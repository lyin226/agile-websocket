package com.agile.websocket.business.service;


import com.agile.websocket.business.dto.GroupDTO;
import com.agile.websocket.business.entity.GroupChat;
import com.agile.websocket.business.entity.User;
import com.agile.websocket.business.mapper.GroupChatMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 群聊表 服务实现类
 * </p>
 *
 * @author liuyi
 * @since 2019-05-31
 */
@Service
public class GroupChatServiceImpl extends ServiceImpl<GroupChatMapper, GroupChat> {



    /**
     * 根据群主id获取群聊记录
     * @param userId
     * @return
     */
    public List<GroupChat> getListByGroupId(Long userId) {
        QueryWrapper<GroupChat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_user_id", userId);
        return super.list(queryWrapper);
    }

    /**
     * 根据群成员id获取群聊记录
     * @param userId
     * @return
     */
    public List<GroupChat> getListByOtherId(Long userId) {
        QueryWrapper<GroupChat> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("other_id", "%"+userId+ "%");
        return super.list(queryWrapper);
    }

    /**
     * 根据用户id获取群聊记录
     * @param user
     */
    public void setGroupList(User user) {
        List<GroupChat> userIdList = getListByGroupId(user.getId());
        List<GroupChat> otherIdList = getListByOtherId(user.getId());
        Set<GroupChat> set = new HashSet<>();
        set.addAll(userIdList);
        set.addAll(otherIdList);
        List<GroupChat> tempList = new ArrayList<>();
        tempList.addAll(set);
        List<GroupDTO> resultList = new ArrayList<>();
        for (GroupChat groupChat : tempList) {
            GroupDTO groupDTO = new GroupDTO();
            groupDTO.setGroupId(groupChat.getId());
            groupDTO.setGroupName(groupChat.getGroupName());
            resultList.add(groupDTO);
        }
        user.setGroupList(resultList);
    }

    /**
     * 获取群信息
     * @param groupId
     * @return
     */
    public GroupChat getGroupInfoById(String groupId) {
        return super.getById((Serializable)Long.parseLong(groupId));
    }

}
