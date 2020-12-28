package com.deeppoker.texas.core.algorithm.judge;

import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.algorithm.CardCompose;
import com.deeppoker.texas.core.algorithm.CardComposeJudge;
import com.deeppoker.texas.core.TexasRanking;
import com.deeppoker.texas.core.card.CardTuple;
import com.deeppoker.texas.core.card.CardUtils;
import com.google.common.collect.Lists;

import java.util.List;

public class TwoPairJudge implements CardComposeJudge {
    @Override
    public CardCompose judge(List<Card> cards) {
        CardTuple cardTuple1 = CardUtils.getCardTuple(cards, 2);
        if (cardTuple1 == null) return null;
        CardTuple cardTuple2 = CardUtils.getCardTuple(CardUtils.remove(cards, cardTuple1.getCards()), 2);
        if (cardTuple2 == null) return null;

        CardTuple cardTuple3 = CardUtils.getCardTuple(CardUtils.remove(cards, join(cardTuple1.getCards(), cardTuple2.getCards())), 2);
        if (cardTuple3 == null) {
            Card max = CardUtils.getMaxRanking(CardUtils.remove(cards, join(cardTuple1.getCards(), cardTuple2.getCards())), null);
            List<Card> result = Lists.newArrayList(max);
            result.addAll(cardTuple1.getCards());
            result.addAll(cardTuple2.getCards());
            return new CardCompose(TexasRanking.TWO_PAIR, result);
        } else {
            List<Card> result = Lists.newArrayList(join(cardTuple1.getCards(), cardTuple2.getCards()));
            result.addAll(cardTuple3.getCards());
            CardTuple minCardTuple = cardTuple1;
            if (minCardTuple.getRanking() > cardTuple2.getRanking()) {
                minCardTuple = cardTuple2;
                if (minCardTuple.getRanking() > cardTuple3.getRanking()) {
                    minCardTuple = cardTuple3;
                }
            }
            result.removeAll(minCardTuple.getCards());
            result.add(CardUtils.getMaxRanking(cards, result));
            return new CardCompose(TexasRanking.TWO_PAIR, result);
        }
    }
}
