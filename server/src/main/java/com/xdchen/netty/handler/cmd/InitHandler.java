package com.xdchen.netty.handler.cmd;

import com.alibaba.fastjson.JSONObject;
import com.xdchen.netty.model.*;
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
			data.put("currentCards", room.getCurrentCards());
			String[] users = new String[room.getBeginUsers().length];
			int i = 0;
			for (User user : room.getBeginUsers()) {
				users[i++] = user.getUsername();
			}
			data.put("users", users);
			response.setRetData(data);
			logger.info("用户[{}]掉线，重新获取卡牌", beginUsers[index].getUsername());
			request.getChannel().writeAndFlush(new TextWebSocketFrame(response.getResponseString()));

			if (index == room.getCurrentIndex()) {
				logger.info("用户[{}]可以出牌", beginUsers[index].getUsername());
				response.setCmd(Constant.Cmd.CAN_PLAY.value);
				response.setRetData(null);
				request.getChannel().writeAndFlush(new TextWebSocketFrame(response.getResponseString()));
			}
		}
	}
}
