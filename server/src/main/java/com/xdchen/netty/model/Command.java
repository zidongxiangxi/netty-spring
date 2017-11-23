package com.xdchen.netty.model;

import net.sf.json.JSONObject;

public class Command {
	private int cmd;
	private Object data;

	public Command() {}

	public Command(int cmd, JSONObject data) {
		this.cmd = cmd;
		this.data = data;
	}

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
