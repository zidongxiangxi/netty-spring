package com.xdchen.netty.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xdchen.netty.model.Card;
import com.xdchen.netty.model.CardsInfo;

import java.util.*;

public class CardService implements ICardService {
    public static final int KING_VALUE = 16;
    enum CardType {
        SINGLE(1, 1, "单张"), DOUBLE(2, 1, "对子或连对"), STRAIGHT(3, 1, "顺子"), THREE(4, 1, "三条"),
        THREE_AND_ONE(5, 1, "三带一"), THREE_AND_TWO(6, 1, "三带二"), FOUR(7, 2, "炸弹"),
        FOUR_AND_TWO(8, 2, "四带二"), FOUR_AND_FOUR(9, 2, "四带四"), KING(10, 3, "王炸");
        public int type;
        public int level;
        public String desc;
        CardType(int type, int level, String desc) {
            this.type = type;
            this.level = level;
            this.desc = desc;
        }
    }
    @Override
    public List<Card> initCards() {
        List<Card> cards = new ArrayList<>(54);
        for (int i = 1; i < 14; i++) {
            for (int j = 1; j < 5; j++) {
                cards.add(new Card(i + 2, j));
            }
        }
        cards.add(new Card(16, 1));
        cards.add(new Card(16, 2));
        return cards;
    }

    @Override
    public void shuffleCards(List<Card> cards) {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int index1 = random.nextInt(54);
            int index2 = random.nextInt(54);
            Card card1 = cards.get(index1);
            cards.set(index1, cards.get(index2));
            cards.set(index2, card1);
        }
    }

    @Override
    public List<Card> dealCards(Map<String, List<Card>> userCardsMap, List<Card> cards) {
        List<String> users = new ArrayList<>(3);
        for (String username : userCardsMap.keySet()) {
            userCardsMap.get(username).clear();
            users.add(username);
        }
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 3; j++) {
                userCardsMap.get(users.get(j)).add(cards.remove(0));
            }
        }
        return cards;
    }

    @Override
    public CardsInfo getCardsInfo(JSONArray cards) {
        cards.sort((a, b) -> {
            int number1 = ((JSONObject) a).getIntValue("number");
            int number2 = ((JSONObject) b).getIntValue("number");
            return number1 - number2;
        });
        CardsInfo cardsInfo;
        if (cards.size() == 1) {
            cardsInfo = new CardsInfo();
            cardsInfo.setType(CardType.SINGLE.type);
            cardsInfo.setLevel(CardType.SINGLE.level);
            cardsInfo.setLength(1);
            cardsInfo.setValue(cards.getJSONObject(0).getIntValue("number"));
            return cardsInfo;
        }
        cardsInfo = isDoubleForSelectedCards(cards);
        cardsInfo = cardsInfo == null ? isStraightForSelectedCards(cards) : cardsInfo;
        cardsInfo = cardsInfo == null ? isAaaForSelectedCards(cards) : cardsInfo;
        cardsInfo = cardsInfo == null ? isAaabForSelectedCards(cards) : cardsInfo;
        cardsInfo = cardsInfo == null ? isAaabbForSelectedCards(cards) : cardsInfo;
        cardsInfo = cardsInfo == null ? isAaaaForSelectedCards(cards) : cardsInfo;
        cardsInfo = cardsInfo == null ? isAaaabcForSelectedCards(cards) : cardsInfo;
        cardsInfo = cardsInfo == null ? isAaaabbccForSelectedCards(cards) : cardsInfo;
        cardsInfo = cardsInfo == null ? isKingForSelectedCards(cards) : cardsInfo;
        return cardsInfo;
    }

    @Override
    public boolean moreThan(CardsInfo cardsInfo1, CardsInfo cardsInfo2) {
        return cardsInfo1.getLevel() > cardsInfo2.getLevel()
                || (cardsInfo1.getType() == cardsInfo2.getType()
                && cardsInfo1.getLength() == cardsInfo2.getLength()
                && cardsInfo1.getValue() > cardsInfo2.getValue());
    }

    private CardsInfo isDoubleForSelectedCards(JSONArray cards) {
        int size = cards.size();
        if (size < 2 || size % 2 != 0) {
            return null;
        }
        int lastValue = cards.getJSONObject(0).getIntValue("number") - 1;
        for (int i = 0; i < size / 2; i += 2) {
            int value = cards.getJSONObject(i).getIntValue("number"),
                    nextValue = cards.getJSONObject(i + 1).getIntValue("number");
            if (value != (lastValue + 1) || !(value == nextValue)) {
                return null;
            }
        }
        CardsInfo cardsInfo = new CardsInfo();
        cardsInfo.setType(CardType.DOUBLE.type);
        cardsInfo.setLevel(CardType.DOUBLE.level);
        cardsInfo.setLength(size);
        cardsInfo.setValue(cards.getJSONObject(size - 1).getIntValue("number"));
        return cardsInfo;
    }

    private CardsInfo isStraightForSelectedCards(JSONArray cards) {
        int size = cards.size();
        if (size < 5) {
            return null;
        }
        for (int i = 1; i < size; i++) {
            int value = cards.getJSONObject(i).getIntValue("number"),
                    lstValue = cards.getJSONObject(i - 1).getIntValue("number");
            if ((value != lstValue + 1) || (value >= 16)) {
                return null;
            }
        }
        CardsInfo cardsInfo = new CardsInfo();
        cardsInfo.setType(CardType.STRAIGHT.type);
        cardsInfo.setLevel(CardType.STRAIGHT.level);
        cardsInfo.setLength(size);
        cardsInfo.setValue(cards.getJSONObject(size - 1).getIntValue("number"));
        return cardsInfo;
    }

    private CardsInfo isAaaForSelectedCards(JSONArray cards) {
        int size = cards.size();
        if (size < 3 || size % 3 != 0) {
            return null;
        }
        int lastValue = 0;
        for (int i = 0; i < size; i += 3) {
            int value = cards.getJSONObject(i).getIntValue("number"),
                    secondValue = cards.getJSONObject(i + 1).getIntValue("number"),
                    thirdValue = cards.getJSONObject(i + 2).getIntValue("number");
            if (value != (lastValue + 1) || !(value == secondValue && secondValue == thirdValue)) {
                return null;
            }
        }
        CardsInfo cardsInfo = new CardsInfo();
        cardsInfo.setType(CardType.THREE.type);
        cardsInfo.setLevel(CardType.THREE.level);
        cardsInfo.setLength(size);
        cardsInfo.setValue(cards.getJSONObject(size - 1).getIntValue("number"));
        return cardsInfo;
    }

    private CardsInfo isAaabForSelectedCards(JSONArray cards) {
        int size = cards.size();
        if (size < 4 || size % 4 != 0) {
            return null;
        }
        JSONArray aaaCards = new JSONArray(),
                otherCards = new JSONArray();
        int i = 0;
        for (; i < size - 2;) {
            int value = cards.getJSONObject(i).getIntValue("number"),
                    secondValue = cards.getJSONObject(i + 1).getIntValue("number"),
                    thirdValue = cards.getJSONObject(i + 2).getIntValue("number");
            if (value == secondValue && secondValue == thirdValue) {
                aaaCards.add(cards.getJSONObject(i));
                aaaCards.add(cards.getJSONObject(i + 1));
                aaaCards.add(cards.getJSONObject(i + 2));
                i += 3;
            } else {
                otherCards.add(cards.getJSONObject(i));
                i++;
            }
        }
        if (i < size) {
            for (; i < size; i++) {
                otherCards.add(cards.getJSONObject(i));
            }
        }
        CardsInfo cardsInfo = isAaaForSelectedCards(aaaCards);
        if (cardsInfo == null || (aaaCards.size() / 3f != otherCards.size())) {
            return null;
        }
        cardsInfo.setType(CardType.THREE_AND_ONE.type);
        cardsInfo.setLevel(CardType.THREE_AND_ONE.level);
        cardsInfo.setLength(size);
        cardsInfo.setValue(aaaCards.getJSONObject(aaaCards.size() - 1).getIntValue("number"));
        return cardsInfo;
    }

    private CardsInfo isAaabbForSelectedCards(JSONArray cards) {
        int size = cards.size();
        if (size < 5 || size % 5 != 0) {
            return null;
        }
        JSONArray aaaCards = new JSONArray(),
                otherCards = new JSONArray();
        int i = 0;
        for (; i < size - 1;) {
            int value = cards.getJSONObject(i).getIntValue("number"),
                    secondValue = cards.getJSONObject(i + 1).getIntValue("number");
            if (i < size - 2) {
                int thirdValue = cards.getJSONObject(i + 2).getIntValue("number");
                if (value == secondValue && secondValue == thirdValue) {
                    aaaCards.add(cards.getJSONObject(i));
                    aaaCards.add(cards.getJSONObject(i + 1));
                    aaaCards.add(cards.getJSONObject(i + 2));
                }
            } else if (value == secondValue) {
                otherCards.add(cards.getJSONObject(i));
                otherCards.add(cards.getJSONObject(i + 1));
            } else {
                return null;
            }
        }
        if (i < size) {
            for (; i < size; i++) {
                otherCards.add(cards.getJSONObject(i));
            }
        }
        CardsInfo cardsInfo = isAaaForSelectedCards(aaaCards);
        if (cardsInfo == null || (aaaCards.size() / 3f != otherCards.size() / 2f)) {
            return null;
        }
        cardsInfo.setType(CardType.THREE_AND_TWO.type);
        cardsInfo.setLevel(CardType.THREE_AND_TWO.level);
        cardsInfo.setLength(size);
        cardsInfo.setValue(aaaCards.getJSONObject(aaaCards.size() - 1).getIntValue("number"));
        return cardsInfo;
    }

    private CardsInfo isAaaaForSelectedCards(JSONArray cards) {
        int size = cards.size();
        if (size < 4 || size % 4 != 0) {
            return null;
        }
        int lastValue = 0;
        for (int i = 0; i < size; i += 4) {
            int value = cards.getJSONObject(i).getIntValue("number"),
                    secondValue = cards.getJSONObject(i + 1).getIntValue("number"),
                    thirdValue = cards.getJSONObject(i + 2).getIntValue("number"),
                    fourthValue = cards.getJSONObject(i + 3).getIntValue("number");
            if (value != (lastValue + 1) || !(value == secondValue && secondValue == thirdValue && thirdValue == fourthValue)) {
                return null;
            }
        }
        CardsInfo cardsInfo = new CardsInfo();
        cardsInfo.setType(CardType.FOUR.type);
        cardsInfo.setLevel(CardType.FOUR.level);
        cardsInfo.setLength(size);
        cardsInfo.setValue(cards.getJSONObject(size - 1).getIntValue("number"));
        return cardsInfo;
    }

    private CardsInfo isAaaabcForSelectedCards(JSONArray cards) {
        int size = cards.size();
        if (size < 6 || size % 6 != 0) {
            return null;
        }
        JSONArray aaaaCards = new JSONArray(),
                otherCards = new JSONArray();
        int i = 0;
        for (; i < size - 3;) {
            int value = cards.getJSONObject(i).getIntValue("number"),
                    secondValue = cards.getJSONObject(i + 1).getIntValue("number"),
                    thirdValue = cards.getJSONObject(i + 2).getIntValue("number"),
                    fourthValue = cards.getJSONObject(i + 3).getIntValue("number");
            if (value == secondValue
                    && secondValue == thirdValue
                    && thirdValue == fourthValue) {
                aaaaCards.add(cards.getJSONObject(i));
                aaaaCards.add(cards.getJSONObject(i + 1));
                aaaaCards.add(cards.getJSONObject(i + 2));
                aaaaCards.add(cards.getJSONObject(i + 3));
                i += 4;
            } else {
                otherCards.add(cards.getJSONObject(i));
                i++;
            }
        }
        if (i < size) {
            for (; i < size; i++) {
                otherCards.add(cards.getJSONObject(i));
            }
        }
        CardsInfo cardsInfo = isAaaForSelectedCards(aaaaCards);
        if (cardsInfo == null || (aaaaCards.size() / 4f != otherCards.size() / 2f)) {
            return null;
        }
        cardsInfo.setType(CardType.FOUR_AND_TWO.type);
        cardsInfo.setLevel(CardType.FOUR_AND_TWO.level);
        cardsInfo.setLength(size);
        cardsInfo.setValue(aaaaCards.getJSONObject(aaaaCards.size() - 1).getIntValue("number"));
        return cardsInfo;
    }

    private CardsInfo isAaaabbccForSelectedCards(JSONArray cards) {
        int size = cards.size();
        if (size < 8 || size % 8 != 0) {
            return null;
        }
        JSONArray aaaaCards = new JSONArray(),
                otherCards = new JSONArray();
        int i = 0;
        for (; i < size - 1;) {
            int value = cards.getJSONObject(i).getIntValue("number"),
                    secondValue = cards.getJSONObject(i + 1).getIntValue("number");
            if (i < size - 3) {
                int thirdValue = cards.getJSONObject(i + 2).getIntValue("number"),
                        fourthValue = cards.getJSONObject(i + 3).getIntValue("number");
                if (value == secondValue
                        && secondValue == thirdValue
                        && thirdValue == fourthValue) {
                    aaaaCards.add(cards.getJSONObject(i));
                    aaaaCards.add(cards.getJSONObject(i + 1));
                    aaaaCards.add(cards.getJSONObject(i + 2));
                    aaaaCards.add(cards.getJSONObject(i + 3));
                    i += 4;
                    continue;
                }
            }
            if (value == secondValue) {
                otherCards.add(cards.getJSONObject(i));
                otherCards.add(cards.getJSONObject(i + 1));
                i += 2;
            } else {
                return null;
            }
        }
        if (i < size) {
            for (; i < size; i++) {
                otherCards.add(cards.getJSONObject(i));
            }
        }
        CardsInfo cardsInfo = isAaaForSelectedCards(aaaaCards);
        if (cardsInfo == null || (aaaaCards.size() != otherCards.size())) {
            return null;
        }
        cardsInfo.setType(CardType.FOUR_AND_FOUR.type);
        cardsInfo.setLevel(CardType.FOUR_AND_FOUR.level);
        cardsInfo.setLength(size);
        cardsInfo.setValue(aaaaCards.getJSONObject(aaaaCards.size() - 1).getIntValue("number"));
        return cardsInfo;
    }

    private CardsInfo isKingForSelectedCards(JSONArray cards) {
        int size = cards.size();
        if (size == 2 && cards.getJSONObject(0).getIntValue("number") == KING_VALUE && cards.getJSONObject(1).getIntValue("number") == KING_VALUE) {
            CardsInfo cardsInfo = new CardsInfo();
            cardsInfo.setType(CardType.KING.type);
            cardsInfo.setLevel(CardType.KING.level);
            cardsInfo.setLength(size);
            cardsInfo.setValue(KING_VALUE);
        }
        return null;
    }
}
