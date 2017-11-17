package com.xdchen.netty.model;

public class Card {
    private int number;

    private int color;

    public Card(int number, int color) {
        if (number < 1 || color < 1) {
            throw new RuntimeException("参数不合法");
        }
        this.number = number;
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
