package com.agile.websocket.business.mapper;


import com.agile.websocket.business.dto.UserDTO;
import com.agile.websocket.business.entity.SingleChat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 单聊表 Mapper 接口
 * </p>
 *
 * @author liuyi
 * @since 2019-05-31
 */
public interface SingleChatMapper extends BaseMapper<SingleChat> {


    /**
     * 根据用户id获取单聊记录
     * @param userId
     * @return
     */
    List<UserDTO> getListByUserId(Long userId);


    /**
     * 根据对方用户id获取单聊记录
     * @param userId
     * @return
     */
    List<UserDTO> getListByOtherUserId(Long userId);


}
