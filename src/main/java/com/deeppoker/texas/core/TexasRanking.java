package com.deeppoker.texas.core;

public enum TexasRanking {
    HIGH_CARD("高牌"),
    ONE_PAIR("一对"),
    TWO_PAIR("两对"),
    THREE_OF_A_KIND("三条"),
    STRAIGHT("顺子"),
    FLUSH("同花"),
    FULL_HOUSE("葫芦"),
    FOUR_OF_A_KIND("四条"),
    STRAIGHT_FLUSH("同花顺"),
    ROYAL_FLUSH("皇家同花顺");

    public final String name;

    TexasRanking(String name) {
        this.name = name;
    }
}
