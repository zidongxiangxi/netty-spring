package com.xdchen.netty.handler.dispatch;


import com.xdchen.netty.handler.cmd.GameHandler;
import com.xdchen.netty.model.GameRequest;
import com.xdchen.netty.model.GameResponse;
import com.xdchen.netty.model.MessageQueue;
import com.xdchen.netty.utils.ExceptionUtils;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

/**
 * @project: netty-learn
 * @Title: HandlerDispatcher.java
 * @Package: com.xdchen.netty.dispatcher
 * @author: xdchen
 * @email:
 * @date: 2017年11月16日 下午2:15:04
 * @description:
 * @version:
 */
public class HandlerDispatcher implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(HandlerDispatcher.class);
	private final Map<Integer, MessageQueue> sessionMsgQ;
	private Executor messageExecutor;
	private Map<Integer, GameHandler> handlerMap;
	private boolean running;
	private long sleepTime;

	public HandlerDispatcher() {
		this.sessionMsgQ = new ConcurrentHashMap<>();

		this.running = true;
		this.sleepTime = 200L;
	}

	public void setHandlerMap(Map<Integer, GameHandler> handlerMap) {
		this.handlerMap = handlerMap;
	}

	public void setMessageExecutor(Executor messageExecutor) {
		this.messageExecutor = messageExecutor;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void addMessageQueue(Integer channelId, MessageQueue messageQueue) {
		this.sessionMsgQ.put(channelId, messageQueue);
	}

	public void removeMessageQueue(Channel channel) {
		MessageQueue queue = (MessageQueue) this.sessionMsgQ.remove(channel);
		if (queue != null)
			queue.clear();
	}

	public void addMessage(GameRequest request) {
		try {
			MessageQueue messageQueue = this.sessionMsgQ
					.get(Integer.valueOf(request.getChannel().hashCode()));

			if (messageQueue == null) {
				messageQueue = new MessageQueue(new ConcurrentLinkedQueue<>());

				this.sessionMsgQ.put(Integer.valueOf(request.getChannel().hashCode()), messageQueue);
				messageQueue.add(request);
			} else {
				messageQueue.add(request);
			}
		} catch (Exception e) {
			HandlerDispatcher.logger.error(ExceptionUtils.getStackTrace(e));
		}
	}

	@Override
	public void run() {
		while (this.running) {
			try {
				for (MessageQueue messageQueue : sessionMsgQ.values())
					if ((messageQueue != null) && (messageQueue.size() > 0) && (!messageQueue.isRunning())) {
						MessageWorker messageWorker = new MessageWorker(messageQueue);

						this.messageExecutor.execute(messageWorker);
					}
			} catch (Exception e) {
				HandlerDispatcher.logger.error(ExceptionUtils.getStackTrace(e));
			}
			try {
				Thread.sleep(this.sleepTime);
			} catch (InterruptedException e) {
				HandlerDispatcher.logger.error(ExceptionUtils.getStackTrace(e));
			}
		}
	}

	public void stop() {
		this.running = false;
	}

	public MessageQueue getUserMessageQueue(Channel channel) {
		return this.sessionMsgQ.get(channel);
	}

	private final class MessageWorker implements Runnable {
		private final MessageQueue messageQueue;
		private GameRequest request;

		private MessageWorker(MessageQueue messageQueue) {
			messageQueue.setRunning(true);
			this.messageQueue = messageQueue;
			this.request = messageQueue.getRequestQueue().poll();
		}

		@Override
		public void run() {
			try {
				handMessageQueue();
			} catch (Exception e) {
				HandlerDispatcher.logger.error(ExceptionUtils.getStackTrace(e));
			} finally {
				this.messageQueue.setRunning(false);
			}
		}

		private void handMessageQueue() {
			int commandId = this.request.getCommandId();
			GameResponse response = new GameResponse(this.request.getCommand().getId(), request.getChannel());
			GameHandler handler = HandlerDispatcher.this.handlerMap.get(commandId);
			if (handler != null)
				handler.execute(this.request, response);
			else {
				HandlerDispatcher.logger.warn("指令 [{}]找不到", commandId);
			}
//			this.request.getCtx().channel().writeAndFlush(new TextWebSocketFrame(response.getResponseString()));
		}
	}
}
