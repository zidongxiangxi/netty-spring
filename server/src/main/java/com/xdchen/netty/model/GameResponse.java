package com.xdchen.netty.model;

import io.netty.channel.Channel;
import net.sf.json.JSONObject;


public class GameResponse {
    public final static int SUCCESS_CODE = 0;
	private int code;
	private Command command;
	private Object retData;
	private Channel channel;

	public GameResponse(Command command) {
		this.code = SUCCESS_CODE;
		this.command = command;
		this.retData = null;
		this.channel = null;
	}

    public GameResponse(Command command, Channel channel) {
        this.code = SUCCESS_CODE;
        this.command = command;
        this.retData = null;
        this.channel = channel;
    }

	public GameResponse(int code, Command command, Channel channel) {
		this.code = code;
		this.command = command;
		this.retData = null;
		this.channel = channel;
	}

	public GameResponse(int code, Command command, Object retData, Channel channel) {
		this.code = code;
		this.command = command;
		this.retData = retData;
        this.channel = channel;
    }

	public String getResponseString() {
		JSONObject result = new JSONObject();
		result.put("code", code);
		result.put("cmd", command.getId());
		result.put("data", retData);
		return result.toString();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public Object getRetData() {
		return retData;
	}

	public void setRetData(Object retData) {
		this.retData = retData;
	}

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
