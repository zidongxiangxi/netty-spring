function Command() {
    var $container = $(".container"),
        $leftContainer = $("#left-container"),
        $centerContainer = $("#center-container"),
        $rightContainer = $("#right-container");

    function func_dealResponse(result) {
        if (result.code == 0) {
            var cmd = result.cmd;
            if (cmd == 1) {
                func_initCards(result.data);
                $container.show();
            } else if (cmd == 2) {
                $begin.show();
                $begin.removeAttr("disabled");
            } else if (cmd == 3) {
                $begin.hide();
                $begin.attr("disabled", "disabled");
            } else if (cmd == 4) {
                if ($begin.is(":visible")) {
                    $begin.hide();
                    $begin.attr("disabled", "disabled");
                }
                func_initCards(result.data);
                $container.show();
            } else if (cmd == 5) {
                $buttons.show();
            } else if (cmd == 6) {
                $buttons.hide();
            } else if (cmd == 9) {

            } else if (cmd == 10) {
                CARD.lastCards = null;
            } else if (cmd == 11) {
                alert("游戏结束");
            }
        }
    }

    function func_initCards(data) {
        $leftContainer.html("");
        $centerContainer.html("");
        $rightContainer.html("");
        var i = 0;
        for (; i < data.leftCount; i++) {
            $leftContainer.append('<div class="card card-cover" style="top: ' + (i * 10 + 100) + 'px; z-index: ' + (i + 1) + '"></div>');
        }
        for (i = 0; i < data.rightCount; i++) {
            $rightContainer.append('<div class="card card-cover" style="top: ' + (i * 10 + 100) + 'px; z-index: ' + (i + 1) + '"></div>');
        }
        var cards = data.cards;
        cards.sort(function(a, b) {
            var diff = a.number - b.number;
            return diff == 0 ? b.color - a.color : diff;
        });
        for (i = 0; i < cards.length; i++) {
            var card = cards[i];
            var backImage = "url('/resources/images/puke/" + card.number + '_' + card.color +  ".jpg')";
            $centerContainer.append('<div class="card my-card" data-number="'+ card.number + '" data-color="' + card.color + '" style="left: ' + (i * 25 + 100) + 'px; z-index: ' + (i + 1) + ';background-image: ' + backImage + '"></div>');
        }
        $(".my-card").click(function () {
            var $self = $(this);
           if ($self.data("isClick") == 1) {
               $self.removeClass("click");
               $self.data("isClick", "0");
           } else {
               $self.addClass("click");
               $self.data("isClick", "1");
           }
        });
    }

    this.dealResponse = func_dealResponse;
    this.initCards = func_initCards;
}
var COMMAND = new Command();


