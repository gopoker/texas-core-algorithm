package com.deeppoker.texas.core.algorithm;

import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.Poker;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface CardComposeJudge {

    CardCompose judge(List<Card> cards);


    default List<Card> getCardBySuit(List<Card> cards, Poker.Suit suit) {
        return cards.stream().filter(c -> c.suit == suit).collect(Collectors.toList());
    }

    default List<Card> getMax5Sequence(List<Card> cards) {
        Collections.sort(cards);
        List<Card> sequenceList = Lists.newArrayList();

        Card cardPrevious = null;
        for (Card card : cards) {
            if (cardPrevious != null) {
                if ((card.getRanking() - cardPrevious.getRanking()) == 1) {
                    if (sequenceList.size() == 0) {
                        sequenceList.add(cardPrevious);
                    }
                    sequenceList.add(card);
                } else {
                    if (sequenceList.size() >= 5) {
                        break;
                    }
                    sequenceList.clear();
                }
            }
            cardPrevious = card;
        }

        return (sequenceList.size() >= 5) ? sequenceList.subList(sequenceList.size() - 5, sequenceList.size()) : null;
    }


    default boolean isEquals(List<Card> cards1, List<Card> cards2) {
        return cards1.containsAll(cards2) && cards2.containsAll(cards1);
    }

    default List<Card> join(List<Card> one, List<Card> two) {
        List<Card> result = Lists.newArrayList(one);
        result.addAll(two);
        return result;
    }
}
