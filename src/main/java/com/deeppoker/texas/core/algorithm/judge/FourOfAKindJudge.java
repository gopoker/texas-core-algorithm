package com.deeppoker.texas.core.algorithm.judge;

import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.algorithm.CardCompose;
import com.deeppoker.texas.core.algorithm.CardComposeJudge;
import com.deeppoker.texas.core.TexasRanking;
import com.deeppoker.texas.core.card.CardTuple;
import com.deeppoker.texas.core.card.CardUtils;
import com.google.common.collect.Lists;

import java.util.List;

public class FourOfAKindJudge implements CardComposeJudge {
    @Override
    public CardCompose judge(List<Card> cards) {
        CardTuple cardTuple = CardUtils.getCardTuple(cards, 4);
        if (cardTuple != null) {
            Card card = CardUtils.getMaxRanking(cards, cardTuple.getCards());
            List<Card> result = Lists.newArrayList(cardTuple.getCards());
            result.add(card);
            return new CardCompose(TexasRanking.FOUR_OF_A_KIND, result);
        }
        return null;
    }
}
