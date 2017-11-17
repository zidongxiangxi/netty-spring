package com.xdchen.netty.model;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @project: netty-learn
 * @Title: MessageQueue.java
 * @Package: com.xdchen.netty.model
 * @author: xdchen
 * @email:
 * @date: 2017年11月16日 下午2:22:06
 * @description:
 * @version:
 */

public class MessageQueue {
	private Queue<GameRequest> requestQueue;
	private boolean running = false;

	public MessageQueue(ConcurrentLinkedQueue<GameRequest> concurrentLinkedQueue) {
		this.requestQueue = concurrentLinkedQueue;
	}

	public Queue<GameRequest> getRequestQueue() {
		return this.requestQueue;
	}

	public void setRequestQueue(Queue<GameRequest> requestQueue) {
		this.requestQueue = requestQueue;
	}

	public void clear() {
		this.requestQueue.clear();
		this.requestQueue = null;
	}

	public int size() {
		return this.requestQueue != null ? this.requestQueue.size() : 0;
	}

	public boolean add(GameRequest request) {
		return this.requestQueue.add(request);
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isRunning() {
		return this.running;
	}
}