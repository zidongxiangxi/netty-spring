package com.xdchen.netty.handler;

import com.xdchen.netty.model.GameRequest;
import com.xdchen.netty.model.GameResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @project: netty-learn
 * @Title: InitHandler.java
 * @Package: com.xdchen.netty.handler
 * @author: xdchen
 * @email:
 * @date: 2017年11月16日 下午2:27:11
 * @description:
 * @version:
 */
public class InitHandler implements GameHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void execute(GameRequest request, GameResponse response) {
		this.logger.info(JSONObject.fromObject(request.getCommand()).toString());
		response.setRetData("hello world!");
	}
}
