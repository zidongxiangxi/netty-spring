package com.xdchen.netty.handler.cmd;

import com.alibaba.fastjson.JSONObject;
import com.xdchen.netty.model.Card;
import com.xdchen.netty.model.GameRequest;
import com.xdchen.netty.model.GameResponse;
import com.xdchen.netty.model.Room;
import com.xdchen.netty.model.db.User;
import com.xdchen.netty.server.CardServerInitializer;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

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
	private Room room;

	public InitHandler(CardServerInitializer serverInitializer) {
		this.room = serverInitializer.getRoom();
	}

	@Override
	public void execute(GameRequest request, GameResponse response) {
		if (room.isStart()) {
			Map<String, List<Card>> userCards = room.getUserCards();
			User[] beginUsers = room.getBeginUsers();
			int index = room.getChannelList().indexOf(request.getChannel());

			JSONObject data = new JSONObject();
			int leftCount = index == 0 ? userCards.get(beginUsers[2].getUsername()).size() : userCards.get(beginUsers[index - 1].getUsername()).size();
			int rightCount = index == 2 ? userCards.get(beginUsers[0].getUsername()).size() : userCards.get(beginUsers[index + 1].getUsername()).size();
			data.put("leftCount", leftCount);
			data.put("rightCount", rightCount);
			data.put("cards", userCards.get(beginUsers[index].getUsername()));
			response.setRetData(data);
			request.getChannel().writeAndFlush(new TextWebSocketFrame(response.getResponseString()));
			logger.info("用户[{}]掉线，重新获取卡牌", beginUsers[index].getUsername());
		}
	}
}
