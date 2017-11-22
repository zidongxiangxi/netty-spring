function Command() {
    var $container = $(".container"),
        $leftContainer = $("#left-container"),
        $centerContainer = $("#center-container"),
        $rightContainer = $("#right-container");

    function func_dealResponse(result) {
        if (result.code == 0) {
            if (result.cmd == 1) {
                func_initCards(result.data);
                $container.show();
            } else if (result.cmd == 2) {
                $begin.show();
                $begin.removeAttr("disabled");
            } else if (result.cmd == 3) {
                $begin.hide();
                $begin.attr("disabled", "disabled");
            } else if (result.cmd == 4) {

            } else if (result.cmd == 5) {
                if ($begin.is(":visible")) {
                    $begin.hide();
                    $begin.attr("disabled", "disabled");
                }
                func_initCards(result.data);
                $container.show();
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
        for (i = 0; i < cards.length; i++) {
            var card = cards[i];
            var backImage = "url('/resources/images/puke/" + card.number + '_' + card.color +  ".jpg')";
            $centerContainer.append('<div class="card my-card" style="left: ' + (i * 25 + 100) + 'px; z-index: ' + (i + 1) + ';background-image: ' + backImage + '"></div>');
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


