package com.xdchen.netty.server;

import com.xdchen.netty.handler.dispatch.HandlerDispatcher;
import com.xdchen.netty.model.Command;
import com.xdchen.netty.model.GameRequest;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerAdapter extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	private static final Logger logger = LoggerFactory.getLogger(ServerAdapter.class);

	private static final String WEBSOCKET_PATH = "/ws";
	private HandlerDispatcher handlerDispatcher;


	public void setHandlerDispatcher(HandlerDispatcher handlerDispatcher) {
		this.handlerDispatcher = handlerDispatcher;
	}

	public ServerAdapter(HandlerDispatcher handlerDispatcher) {
		this.handlerDispatcher = handlerDispatcher;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		JSONObject commandJson = JSONObject.fromObject(msg.text());
		Command command = (Command) JSONObject.toBean(commandJson, Command.class);
		this.handlerDispatcher.addMessage(new GameRequest(ctx, command));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("服务出现异常：", cause);
		ctx.close();
	}

	private static String getWebSocketLocation(FullHttpRequest req) {
		return "ws://" + req.headers().get("Host") + WEBSOCKET_PATH;
	}

	
}
