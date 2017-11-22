package com.xdchen.netty.model;

import io.netty.channel.Channel;
import net.sf.json.JSONObject;


public class GameResponse {
    public final static int SUCCESS_CODE = 0;
	private int code;
	private int cmd;
	private Object retData;
	private Channel channel;

	public GameResponse(int cmd) {
		this.code = SUCCESS_CODE;
		this.cmd = cmd;
		this.retData = null;
		this.channel = null;
	}

    public GameResponse(int cmd, Channel channel) {
        this.code = SUCCESS_CODE;
        this.cmd = cmd;
        this.channel = channel;
		this.retData = null;
	}

	public GameResponse(int code, int cmd, Channel channel) {
		this.code = code;
		this.cmd = cmd;
		this.channel = channel;
		this.retData = null;
	}

	public GameResponse(int code, int cmd, Channel channel, Object retData) {
		this.code = code;
		this.cmd = cmd;
        this.channel = channel;
		this.retData = retData;
	}

	public String getResponseString() {
		JSONObject result = new JSONObject();
		result.put("code", code);
		result.put("cmd", cmd);
		result.put("data", retData);
		return result.toString();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
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
