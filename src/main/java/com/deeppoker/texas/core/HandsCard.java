package com.deeppoker.texas.core;

import com.deeppoker.texas.core.card.Card;

public class HandsCard {
    public final Card cardA, cardB;

    public HandsCard(Card cardA, Card cardB) {
        this.cardA = cardA;
        this.cardB = cardB;
    }

    @Override
    public String toString() {
        return "[" + cardA + ", " + cardB + "]";
    }


    public String[] toSimpleArray() {
        String[] str = new String[2];
        str[0] = cardA.toShort();
        str[1] = cardB.toShort();
        return str;
    }
}
