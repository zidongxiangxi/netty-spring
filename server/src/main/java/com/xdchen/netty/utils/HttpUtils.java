package com.xdchen.netty.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.CharsetUtil;

/** 
* @project:		netty-learn
* @Title:		HttpUtils.java
* @Package:		com.xdchen.netty.utils
  @author: 		xdchen
* @email:
* @date:		2017年11月16日 上午10:10:31
* @description:
* @version:
*/
public class HttpUtils {
	public static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
		if (res.getStatus().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);

			res.content().writeBytes(buf);
			buf.release();
			HttpHeaders.setContentLength(res, res.content().readableBytes());
		}

		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if ((!HttpHeaders.isKeepAlive(req)) || (res.getStatus().code() != 200))
			f.addListener(ChannelFutureListener.CLOSE);
	}
}
