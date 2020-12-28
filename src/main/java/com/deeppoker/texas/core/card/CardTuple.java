package com.deeppoker.texas.core.card;

import com.google.common.base.Preconditions;

import java.util.List;

public class CardTuple implements Comparable<CardTuple> {
    private List<Card> cards;


    public CardTuple(List<Card> cards) {
        Preconditions.checkArgument(cards.size() > 1);
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int tupleSize() {
        return cards.size();
    }

    public int getRanking() {
        return cards.get(0).getRanking();
    }

    @Override
    public int compareTo(CardTuple that) {
        Preconditions.checkArgument(this.cards.size() == that.cards.size());
        return this.getRanking() - that.getRanking();
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
