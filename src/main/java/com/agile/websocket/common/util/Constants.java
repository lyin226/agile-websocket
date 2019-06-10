package com.agile.websocket.common.util;

import cn.hutool.system.UserInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuyi
 * @date 2019/5/27
 */
public class Constants {

    public static final String NUMBER_CODE_KEY = "agile-websocket:number:code:";

    public static final String USER_ID = "user_id";


    public static Map<String, WebSocketServerHandshaker> webSocketHandshakerMap =
            new ConcurrentHashMap<String, WebSocketServerHandshaker>();

    public static Map<String, ChannelHandlerContext> onlineUserMap =
            new ConcurrentHashMap<String, ChannelHandlerContext>();
//
//    public static Map<String, GroupInfo> groupInfoMap =
//            new ConcurrentHashMap<String, GroupInfo>();

    public static Map<String, UserInfo> userInfoMap =
            new HashMap<String, UserInfo>();


}
