package com.xdchen.netty.service;

import com.xdchen.netty.model.Card;
import io.netty.channel.Channel;

import java.util.List;
import java.util.Map;

public interface ICardService {
    List<Card> initCards();
    void shuffleCards(List<Card> cards);
    List<Card> dealCards(Map<String, List<Card>> userCardsMap, List<Card> cards);
}
