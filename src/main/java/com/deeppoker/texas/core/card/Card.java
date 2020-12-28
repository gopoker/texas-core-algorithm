package com.deeppoker.texas.core.card;

import com.deeppoker.texas.core.Poker;

import java.util.Objects;

public class Card implements Comparable<Card> {
    public final byte       value;
    public       Poker.Suit suit;

    public Card(byte value) {
        this.value = value;

        for (Poker.Suit suit : Poker.Suit.values()) {
            if (value >> 4 == suit.value) {
                this.suit = suit;
                break;
            }
        }
        if (this.suit == null) {
            throw new IllegalArgumentException("Unknown suit of: " + (value >> 4));
        }
    }


    public Poker.Suit getSuit() {
        return suit;
    }

    public int getRanking() {
        return this.value & 0xF;
    }

    @Override
    public int compareTo(Card that) {
        return Integer.compare(this.value & 0xF, that.value & 0xF);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return new String(new char[]{getSuit().symbol, Poker.cardMap.get(getRanking())});
    }

    public String toShort() {
        return new String(new char[]{Poker.cardMap.get(getRanking()), getSuit().code});
    }
}
