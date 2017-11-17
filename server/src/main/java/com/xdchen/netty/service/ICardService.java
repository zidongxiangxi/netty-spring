package com.xdchen.netty.service;

import com.xdchen.netty.model.Card;

import java.util.List;

public interface ICardService {
    List<Card> initCards();
    void shuffleCards(List<Card> cards);
}
