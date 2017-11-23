function Card() {
    var $self = this;
    this.selectedCards = [];
    /*
     * {
     *   "type": 1,   //类别 1-单张， 2-对子或连对， 3-顺子， 4-三条， 5-炸弹， 6-王炸
     *   "level": 1,  //级别，级别高的，能吃级别低的；同级别的，需要类型相同，张数一致，而且最大值较大
     *   "length": 1, //卡牌数
     *   "value": 3,  //卡牌的有效最大值
     *   "cards": [{"number":3, "color":4}]  //卡牌数组
     * }
     * */
    this.lastCards = null;
    this.cardsCanPlayOrNot = func_cardsCanPlayOrNot;
    this.getSelectedCardsArray = func_getSelectedCardsArray;
    this.validateCards = func_validateCards;


    function func_cardsCanPlayOrNot() {
        $self.selectedCards = func_getSelectedCardsArray();
        var selectedCards = $self.selectedCards;
        if (selectedCards.length < 1) {
            alert("请选择要打出的卡牌");
            return false;
        }

        var result = func_validateCards(selectedCards);
        if (!result.isValid) {
            alert("选择的卡牌不符合出牌规则");
            return false;
        }

        if (!$self.lastCards || !$self.lastCards.cards || !$self.lastCards.cards.length < 1) {
            return true;
        }
        var selectedCardsInfo = result.cardsInfo;
        var canPlay = $self.lastCards.level < selectedCardsInfo.level
            || ($self.lastCards.type == selectedCardsInfo.type
            && $self.lastCards.length == selectedCardsInfo.length
            && selectedCardsInfo.value > $self.lastCards.value);
        if (!canPlay) {
            alert("选择的卡牌太小");
        }
        return canPlay;
    }

    function func_validateCards(selectedCards) {
        var result = {isValid: false};
        if (selectedCards.length == 1) {
            result.isValid = true;
        }
        result = result.isValid ? result : isDoubleForSelectedCards(selectedCards);
        result = result.isValid ? result : isStraightForSelectedCards(selectedCards);
        result = result.isValid ? result : isAaaForSelectedCards(selectedCards);
        result = result.isValid ? result : isAaabForSelectedCards(selectedCards);
        result = result.isValid ? result : isAaabbForSelectedCards(selectedCards);
        result = result.isValid ? result : isAaaaForSelectedCards(selectedCards);
        result = result.isValid ? result : isAaaabForSelectedCards(selectedCards);
        result = result.isValid ? result : isAaaabbForSelectedCards(selectedCards);
        result = result.isValid ? result : isKingForSelectedCards(selectedCards);
        return result;

        function isStraightForSelectedCards(selectedCards) {
            var result = {isValid: false};
            if (selectedCards.length < 5) {
                return result;
            }
            for (var i = 1; i < selectedCards.length; i++) {
                if ((selectedCards[i].number != selectedCards[i - 1].number + 1) || (selectedCards[i].number >= 16)) {
                    return result;
                }
            }
            result.isValid = true;
            result.cardsInfo = {
                type: 3,
                level: 1,
                length: selectedCards.length,
                value: selectedCards[selectedCards.length - 1].number,
                cards: selectedCards
            };
            return result;
        }

        function isDoubleForSelectedCards(selectedCards) {
            var result = {isValid: false};
            if (selectedCards.length < 2 || selectedCards.length % 2 != 0) {
                return result;
            }
            var lastValue = selectedCards[0].number - 1;
            for (var i = 0; i < selectedCards.length / 2; i += 2) {
                var value = selectedCards[i].number;
                if (value != (lastValue + 1) || !(selectedCards[i].number == selectedCards[i + 1].number)) {
                    return result;
                }
            }
            result.isValid = true;
            result.cardsInfo = {
                type: 2,
                level: 1,
                length: selectedCards.length,
                value: selectedCards[selectedCards.length - 1].number,
                cards: selectedCards
            };
            return result;
        }

        function isAaaForSelectedCards(selectedCards) {
            var result = {isValid: false};
            if (selectedCards.length < 3 || selectedCards.length % 3 != 0) {
                return result;
            }
            var lastValue = selectedCards[0].number - 1;
            for (var i = 0; i < selectedCards.length / 3; i += 3) {
                var value = selectedCards[i].number;
                if (value != (lastValue + 1) || !(selectedCards[i].number == selectedCards[i + 1].number && selectedCards[i + 1].number == selectedCards[i + 2].number)) {
                    return result;
                }
            }
            result.isValid = true;
            result.cardsInfo = {
                type: 4,
                level: 1,
                length: selectedCards.length,
                value: selectedCards[selectedCards.length - 1].number,
                cards: selectedCards
            };
            return result;
        }

        function  isAaabForSelectedCards(selectedCards) {
            var result = {isValid: false};
            if (selectedCards.length < 4 || selectedCards.length % 4 != 0) {
                return false;
            }
            var aaaCards = [], otherCards = [];
            var i = 0;
            for (; i < selectedCards.length - 2;) {
                if (selectedCards[i].number == selectedCards[i + 1].number && selectedCards[i + 1].number == selectedCards[i + 2].number) {
                    aaaCards.push(selectedCards[i]);
                    aaaCards.push(selectedCards[i + 1]);
                    aaaCards.push(selectedCards[i + 2]);
                    i += 3;
                } else {
                    otherCards.push(selectedCards[i]);
                    i++;
                }
            }
            if (i < selectedCards.length) {
                for (; i < selectedCards.length; i++) {
                    otherCards.push(selectedCards[i]);
                }
            }
            result.isValid = isAaaForSelectedCards(aaaCards) && (aaaCards.length / 3 == otherCards.length);
            if (result.isValid) {
                result.cardsInfo = {
                    type: 4,
                    level: 1,
                    length: selectedCards.length,
                    value: aaaCards[aaaCards.length - 1].number,
                    cards: selectedCards
                };
            }
            return result;
        }

        function  isAaabbForSelectedCards() {
            var result = {isValid: false};
            if (selectedCards.length < 5 || selectedCards.length % 5 != 0) {
                return false;
            }
            var aaaCards = [], otherCards = [];
            var i = 0;
            for (; i < selectedCards.length - 1;) {
                if (i < selectedCards.length - 2 && (selectedCards[i].number == selectedCards[i + 1].number && selectedCards[i + 1].number == selectedCards[i + 2].number)) {
                    aaaCards.push(selectedCards[i]);
                    aaaCards.push(selectedCards[i + 1]);
                    aaaCards.push(selectedCards[i + 2]);
                    i += 3;
                } else if (selectedCards[i].number == selectedCards[i + 1].number) {
                    otherCards.push(selectedCards[i]);
                    otherCards.push(selectedCards[i + 1]);
                    i += 2;
                } else {
                    return result;
                }
            }

            if (i < selectedCards.length) {
                for (; i < selectedCards.length; i++) {
                    otherCards.push(selectedCards[i]);
                }
            }

            result.isValid = isAaaForSelectedCards(aaaCards) && (aaaCards.length / 3 == otherCards.length / 2);
            if (result.isValid) {
                result.cardsInfo = {
                    type: 4,
                    level: 1,
                    length: selectedCards.length,
                    value: aaaCards[aaaCards.length - 1].number,
                    cards: selectedCards
                };
            }
            return result;
        }

        function isAaaaForSelectedCards() {
            var result = {isValid: false};
            if (selectedCards.length < 4 || selectedCards.length % 4 != 0) {
                return result;
            }
            var lastValue = selectedCards[0].number - 1;
            for (var i = 0; i < selectedCards.length / 4; i += 4) {
                var value = selectedCards[i].number;
                if (value != (lastValue + 1)
                    || !(selectedCards[i].number == selectedCards[i + 1].number
                    && selectedCards[i + 1].number == selectedCards[i + 2].number
                    && selectedCards[i + 2].number == selectedCards[i + 3].number)) {
                    return result;
                }
            }

            result.isValid = true;
            if (result.isValid) {
                result.cardsInfo = {
                    type: 5,
                    level: 2,
                    length: selectedCards.length,
                    value: selectedCards[selectedCards.length - 1].number,
                    cards: selectedCards
                };
            }
            return result;
        }

        function isAaaabForSelectedCards() {
            var result = {isValid: false};
            if (selectedCards.length < 5 || selectedCards.length % 5 != 0) {
                return false;
            }
            var aaaaCards = [], otherCards = [];
            var i = 0;
            for (; i < selectedCards.length - 3;) {
                if (selectedCards[i].number == selectedCards[i + 1].number
                    && selectedCards[i + 1].number == selectedCards[i + 2].number
                    && selectedCards[i + 2].number == selectedCards[i + 3].number) {
                    aaaaCards.push(selectedCards[i]);
                    aaaaCards.push(selectedCards[i + 1]);
                    aaaaCards.push(selectedCards[i + 2]);
                    aaaaCards.push(selectedCards[i + 3]);
                    i += 4;
                } else {
                    otherCards.push(selectedCards[i]);
                    i++;
                }
            }
            if (i < selectedCards.length) {
                for (; i < selectedCards.length; i++) {
                    otherCards.push(selectedCards[i]);
                }
            }

            result.isValid = isAaaaForSelectedCards(aaaaCards) && (aaaaCards.length / 4 == otherCards.length);
            if (result.isValid) {
                result.cardsInfo = {
                    type: 5,
                    level: 2,
                    length: selectedCards.length,
                    value: aaaaCards[aaaaCards.length - 1].number,
                    cards: selectedCards
                };
            }
            return result;
        }

        function isAaaabbForSelectedCards() {
            var result = {isValid: false};
            if (selectedCards.length < 6 || selectedCards.length % 6 != 0) {
                return result;
            }
            var aaaaCards = [], otherCards = [];
            var i = 0;
            for (; i < selectedCards.length - 1;) {
                if (i < selectedCards.length - 3 && (selectedCards[i].number == selectedCards[i + 1].number && selectedCards[i + 1].number == selectedCards[i + 2].number && selectedCards[i + 2].number == selectedCards[i + 3].number)) {
                    aaaaCards.push(selectedCards[i]);
                    aaaaCards.push(selectedCards[i + 1]);
                    aaaaCards.push(selectedCards[i + 2]);
                    aaaaCards.push(selectedCards[i + 3]);
                    i += 4;
                } else if (selectedCards[i].number == selectedCards[i + 1].number) {
                    otherCards.push(selectedCards[i]);
                    otherCards.push(selectedCards[i + 1]);
                    i += 2;
                } else {
                    return result;
                }
            }

            if (i < selectedCards.length) {
                for (; i < selectedCards.length; i++) {
                    otherCards.push(selectedCards[i]);
                }
            }
            result.isValid = isAaaaForSelectedCards(aaaaCards) && (aaaaCards.length / 4 == otherCards.length / 2);
            if (result.isValid) {
                result.cardsInfo = {
                    type: 5,
                    level: 2,
                    length: selectedCards.length,
                    value: aaaaCards[aaaaCards.length - 1].number,
                    cards: selectedCards
                };
            }
            return result;
        }

        function isKingForSelectedCards() {
            var result = {};
            result.isValid = selectedCards.length == 2 && selectedCards[0].number == 16 && selectedCards[1].number == 16;
            if (result.isValid) {
                result.cardsInfo = {
                    type: 6,
                    level: 3,
                    length: selectedCards.length,
                    value: 16,
                    cards: selectedCards
                };
            }
            return result;
        }
    }

    function  func_getSelectedCardsArray() {
        var selectedCards = $(".my-card.click"), cards = [];
        for (var i = 0; i < selectedCards.length; i++) {
            var card = {}, target = $(selectedCards[i]);
            card.number = target.data("number");
            card.color = target.data("color");
            cards.push(card);
        }
        return cards;
    }
}
var CARD  = new Card();