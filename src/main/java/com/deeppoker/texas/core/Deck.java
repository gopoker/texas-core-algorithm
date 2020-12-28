package com.deeppoker.texas.core;

import com.deeppoker.texas.core.card.Card;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class Deck {
    private List<Card> cards;
    private Random     random;

    public Deck() {
        this(new Random());
    }

    public Deck(Random random) {
        this.random = random;
        createDeck();
    }

    private void createDeck() {
        cards = Lists.newArrayList(Poker.cards);
    }

    public Card pop() {
        return cards.remove(random.nextInt(cards.size()));
    }
}