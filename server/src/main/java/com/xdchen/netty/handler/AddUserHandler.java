package com.xdchen.netty.handler;

import com.xdchen.netty.exception.BusiException;
import com.xdchen.netty.model.Room;
import com.xdchen.netty.model.db.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddUserHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Logger logger = LoggerFactory.getLogger(AddUserHandler.class);
    private Room room;

    public AddUserHandler(Room room) {
        this.room = room;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest request) throws Exception {
        QueryStringDecoder decoder = new QueryStringDecoder(request.getUri());
        String token = decoder.parameters().get("token").get(0);
        System.out.println("token=" + token);
        if (StringUtils.isBlank(token)) {
            throw new BusiException("没有权限");
        }
        Channel incoming = channelHandlerContext.channel();
        User user = new User();
        user.setUsername(token);
        room.addUser(incoming, user);
        System.out.println("Client:" + incoming.remoteAddress() + "加入");
        channelHandlerContext.fireChannelRead(request.retain());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("服务出现异常：", cause);
        ctx.close();
    }
}
