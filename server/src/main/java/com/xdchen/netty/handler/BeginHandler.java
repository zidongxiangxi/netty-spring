package com.xdchen.netty.handler;

import com.xdchen.netty.exception.BusiException;
import com.xdchen.netty.model.*;
import com.xdchen.netty.server.CardServerInitializer;
import com.xdchen.netty.service.CardService;
import com.xdchen.netty.service.ICardService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

public class BeginHandler implements GameHandler {
    private ICardService cardService = new CardService();
    private Room room;
    public BeginHandler(CardServerInitializer serverInitializer) {
        this.room = serverInitializer.getRoom();
    }

    @Override
    public void execute(GameRequest paramGameRequest, GameResponse paramGameResponse) {
        Channel master = room.getMaster();
        if (paramGameRequest.getChannel() != master) {
            throw new BusiException("不是房主，不能启动游戏");
        }
        List<Card> totalCards = cardService.initCards();
        cardService.shuffleCards(totalCards);
        List<Card> cards = cardService.dealCards(room.getUserCards(), totalCards);
        room.getUserCards().get(master).addAll(cards);
        for (Channel channel : room.getChannels()) {
            paramGameResponse.setRetData(room.getUserCards().get(channel));
            channel.writeAndFlush(new TextWebSocketFrame(paramGameResponse.getResponseString()));
        }
    }
}
