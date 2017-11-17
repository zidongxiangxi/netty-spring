package com.xdchen.netty.model;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class GameRequest {
	private Command command;
	private Channel channel;
	private ChannelHandlerContext ctx;

	public GameRequest(ChannelHandlerContext ctx, Command command) {
		try {
			this.ctx = ctx;
			this.channel = ctx.channel();
			this.command = command;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public int getCommandId() {
		if (command != null) {
			return command.getId();
		}
		return -1;
	}
}