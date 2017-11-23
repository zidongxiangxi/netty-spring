package com.xdchen.netty.model;

public class Constant {
    public enum Cmd {
        INIT_CARDS(1, "初始化卡牌"), CAN_BEGIN(2, "可以开始游戏"), CAN_NOT_BEGIN(3, "不可以开始游戏"), BEGIN(4, "开始游戏"),
        CAN_PLAY(5, "可以出牌"), CAN_NOT_PLAY(6, "不可以出牌"), PLAY(7, "出牌"), NOT_PLAY(8, "不出牌");

        public int value;
        public String desc;
        private Cmd(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }
}
