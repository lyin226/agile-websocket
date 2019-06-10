package com.agile.websocket.business.handler;

import com.agile.websocket.business.entity.GroupChat;
import com.agile.websocket.business.service.GroupChatServiceImpl;
import com.agile.websocket.common.util.Constants;
import com.agile.websocket.common.util.SpringUtils;
import com.agile.websocket.common.util.WebResult;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;

/**
 * @author liuyi
 * @date 2019/6/6
 */

@ChannelHandler.Sharable
@Slf4j
public class WebSocketServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {


    /**
     * 读取完连接的消息后，对消息进行处理
     * 这里主要是处理webSocket请求
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        handlerWebSocketFrame(ctx, msg);
    }


    /**
     * 处理webSocketFrame
     * @param ctx
     * @param frame
     * @throws Exception
     */
    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        //关闭请求
        if (frame instanceof CloseWebSocketFrame) {
            WebSocketServerHandshaker handshaker = Constants.webSocketHandshakerMap.get(ctx.channel().id().asLongText());
            if (handshaker == null) {
                sendErrorMessage(ctx, "不存在客户端连接");
            } else {
                handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            }
            return;
        }
        //ping请求
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        //只支持文本格式，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            sendErrorMessage(ctx, "仅支持文本(Text)格式，不支持二进制消息");
        }
        //客户端发送过来的消息
        String req = ((TextWebSocketFrame)frame).text();
        log.info("服务端收到新消息：" + req);
        JSONObject param = null;
        try {
            param = JSONObject.parseObject(req);
        } catch (Exception e) {
            sendErrorMessage(ctx, "JSON字符串转换出错");
            log.error("WebSocketServerHandler error!" + e.getMessage(), e);
        }
        if (param == null) {
            sendErrorMessage(ctx, "参数为空");
            return;
        }
        String type = (String) param.get("type");
        switch (type) {
            case "REGISTER":
                this.register(param, ctx);
                break;
            case "SINGLE_SENDING":
                this.singleSend(param, ctx);
                break;
            case "GROUP_SENDING":
                this.groupSend(param, ctx);
                break;
            default:
                typeError(ctx);
                break;
        }

    }

    /**
     * 客户端断开连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.remove(ctx);
    }

    /**
     * 异常处理：关闭channel
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    private void sendErrorMessage(ChannelHandlerContext ctx, String errorMsg) {
        String res = WebResult.error(errorMsg).toString();
        ctx.channel().writeAndFlush(new TextWebSocketFrame(res));
    }

    public void register(JSONObject param, ChannelHandlerContext ctx) {
        String userId = getValueByName(param, "userId");
        Constants.onlineUserMap.put(userId, ctx);
        String res = new WebResult().put("type", "REGISTER").toString();
        sendMessage(ctx, res);
        log.info(MessageFormat.format("userId为 {0}的用户等级在线用户表，当前在线人数为：{1}", userId, Constants.onlineUserMap.size()));
    }


    public void singleSend(JSONObject param, ChannelHandlerContext ctx) {
        String fromUserId = getValueByName(param,"fromUserId");
        String toUserId = getValueByName(param, "toUserId");
        String content = getValueByName(param, "content");
        ChannelHandlerContext toUserCtx = Constants.onlineUserMap.get(toUserId);
        if (toUserCtx == null) {
            String res = WebResult.error(MessageFormat.format("userId为 {0} 的用户没有登录！", toUserId)).toString();
            sendMessage(ctx, res);
        } else {
            String res = new WebResult().put("fromUserId", fromUserId).put("content", content).put("type", "SINGLE_SENDING").toString();
            sendMessage(toUserCtx, res);
        }
    }

    public void groupSend(JSONObject param, ChannelHandlerContext ctx) {
        String fromUserId = getValueByName(param,"fromUserId");
        String toGroupId = (String)param.get("toGroupId");
        String content = getValueByName(param, "content");
        GroupChatServiceImpl groupChatService = (GroupChatServiceImpl)SpringUtils.getBean("groupChatServiceImpl");
        GroupChat groupChat = groupChatService.getGroupInfoById(toGroupId);
        if (groupChat == null) {
            String res = WebResult.error("该群id不存在").toString();
            sendMessage(ctx, res);
        } else {
            String res = new WebResult()
                    .put("fromUserId", fromUserId)
                    .put("content", content)
                    .put("toGroupId", toGroupId)
                    .put("type", "GROUP_SENDING")
                    .toString();
            String[] array = groupChat.getOtherId().split(",");
            for (String s : array) {
                ChannelHandlerContext toCtx = Constants.onlineUserMap.get(String.valueOf(s));
                if (toCtx != null) {
                    sendMessage(toCtx, res);
                }
            }
        }
    }


    public void remove(ChannelHandlerContext ctx) {
        Iterator<Map.Entry<String, ChannelHandlerContext>> iterator =
                Constants.onlineUserMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, ChannelHandlerContext> entry = iterator.next();
            if (entry.getValue() == ctx) {
                log.info("正在移除握手实例...");
                Constants.webSocketHandshakerMap.remove(ctx.channel().id().asLongText());
                log.info(MessageFormat.format("已移除握手实例，当前握手实例总数为：{0}"
                        , Constants.webSocketHandshakerMap.size()));
                iterator.remove();
                log.info(MessageFormat.format("userId为 {0} 的用户已退出聊天，当前在线人数为：{1}"
                        , entry.getKey(), Constants.onlineUserMap.size()));
                break;
            }
        }
    }

    public void typeError(ChannelHandlerContext ctx) {
        String responseJson = WebResult
                .error("该类型不存在！")
                .toString();
        sendMessage(ctx, responseJson);
    }


    private void sendMessage(ChannelHandlerContext ctx, String message) {
        ctx.channel().writeAndFlush(new TextWebSocketFrame(message));
    }

    public  String getValueByName(JSONObject param, String name) {
        return String.valueOf(param.get(name));
    }

}
