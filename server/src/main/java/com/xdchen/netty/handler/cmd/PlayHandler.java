package com.xdchen.netty.handler.cmd;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xdchen.netty.model.*;
import com.xdchen.netty.server.CardServerInitializer;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class PlayHandler implements GameHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Room room;

    public PlayHandler(CardServerInitializer serverInitializer) {
        this.room = serverInitializer.getRoom();
    }
    @Override
    public void execute(GameRequest request, GameResponse response) {
        JSONArray cards = (JSONArray) request.getCommand().getData();
        room.setCurrentCards(request.getChannel(), cards);
        //从服务器，删除对应玩家出的卡牌
        String username = room.getUsernameByChannel(request.getChannel());
        List<Card> userCards = room.getUserCards().get(username);
        this.removeCards(userCards, cards);

        //通知出的牌
        JSONObject retData = new JSONObject();
        retData.put("cards", cards);
        retData.put("user", room.getUsernameByChannel(request.getChannel()));
        GameResponse gameResponse = new GameResponse(Constant.Cmd.NOTIFY.value);
        gameResponse.setRetData(retData);
        room.getChannels().writeAndFlush(new TextWebSocketFrame(gameResponse.getResponseString()));
        logger.info("通知所有用户，当前用户出的牌");

        gameResponse.setRetData(null);
        if (userCards.size() == 0) {
            //游戏结束
            room.gameOver();
            gameResponse.setCmd(Constant.Cmd.GAME_OVER.value);
            room.getChannels().writeAndFlush(new TextWebSocketFrame(gameResponse.getResponseString()));
            logger.info("游戏结束");
        } else {
            //通知下一位玩家，可以出牌
            int nextUserIndex = room.nextTurn();
            gameResponse.setCmd(Constant.Cmd.CAN_PLAY.value);
            room.getChannelList().get(nextUserIndex).writeAndFlush(new TextWebSocketFrame(gameResponse.getResponseString()));
            logger.info("通知下一位用户可以出牌");
        }
    }

    private void removeCards(List<Card> cardList, JSONArray targetCards) {
        Iterator<Card> cardIterator = cardList.iterator();
        while (cardIterator.hasNext()) {
            Card card = cardIterator.next();
            for (int i = 0; i < targetCards.size(); i++) {
                JSONObject targetCard = targetCards.getJSONObject(i);
                if (card.getNumber() == targetCard.getIntValue("number") && card.getColor() == targetCard.getIntValue("color")) {
                    cardIterator.remove();
                }
            }
        }
    }
}
