package com.xdchen.netty.model;

import net.sf.json.JSONObject;

public class Command {
	private int id;
	private Object data;

	public Command() {}

	public Command(int id, JSONObject data) {
		this.id = id;
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
