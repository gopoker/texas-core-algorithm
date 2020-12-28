package com.deeppoker.texas.core.algorithm.judge;

import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.Poker;
import com.deeppoker.texas.core.algorithm.CardCompose;
import com.deeppoker.texas.core.algorithm.CardComposeJudge;
import com.deeppoker.texas.core.TexasRanking;
import com.google.common.collect.Lists;

import java.util.List;

public class RoyalFlushJudge implements CardComposeJudge {
    private List<List<Card>> RoyalFlushList = Lists.newArrayList(
            Lists.newArrayList(Poker.cards.get(8), Poker.cards.get(9), Poker.cards.get(10), Poker.cards.get(11), Poker.cards.get(12)),
            Lists.newArrayList(Poker.cards.get(21), Poker.cards.get(22), Poker.cards.get(23), Poker.cards.get(24), Poker.cards.get(25)),
            Lists.newArrayList(Poker.cards.get(34), Poker.cards.get(35), Poker.cards.get(36), Poker.cards.get(37), Poker.cards.get(38)),
            Lists.newArrayList(Poker.cards.get(47), Poker.cards.get(48), Poker.cards.get(49), Poker.cards.get(50), Poker.cards.get(51)));

    @Override
    public CardCompose judge(List<Card> cards) {
        if (cards.size() < 5) return null;
        for (List<Card> cardLists : RoyalFlushList) {
            if (cards.containsAll(cardLists)) {
                return new CardCompose(TexasRanking.ROYAL_FLUSH, cardLists);
            }
        }
        return null;
    }
}
