package com.xdchen.netty.handler.cmd;

import com.xdchen.netty.model.Constant;
import com.xdchen.netty.model.GameRequest;
import com.xdchen.netty.model.GameResponse;
import com.xdchen.netty.model.Room;
import com.xdchen.netty.server.CardServerInitializer;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotPlayHandler implements GameHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Room room;

    public NotPlayHandler(CardServerInitializer serverInitializer) {
        this.room = serverInitializer.getRoom();
    }

    @Override
    public void execute(GameRequest request, GameResponse response) {
        room.getChannels().writeAndFlush(new TextWebSocketFrame(response.getResponseString()));
        logger.info("通知所有用户，当前用户不出牌");
        int nextUserIndex = room.nextTurn();
        if (nextUserIndex == room.getCardsOwnerIndex()) {
            response.setCmd(Constant.Cmd.CAN_PLAY_ANY_CARD.value);
            response.setRetData(null);
            room.getChannelList().get(nextUserIndex).writeAndFlush(new TextWebSocketFrame(response.getResponseString()));
        }
        response.setCmd(Constant.Cmd.CAN_PLAY.value);
        room.getChannelList().get(nextUserIndex).writeAndFlush(new TextWebSocketFrame(response.getResponseString()));
    }
}
