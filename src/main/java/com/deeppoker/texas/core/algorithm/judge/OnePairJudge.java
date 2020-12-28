package com.deeppoker.texas.core.algorithm.judge;

import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.algorithm.CardCompose;
import com.deeppoker.texas.core.algorithm.CardComposeJudge;
import com.deeppoker.texas.core.TexasRanking;
import com.deeppoker.texas.core.card.CardTuple;
import com.deeppoker.texas.core.card.CardUtils;

import java.util.List;

public class OnePairJudge implements CardComposeJudge {
    @Override
    public CardCompose judge(List<Card> cards) {
        CardTuple cardTuple = CardUtils.getCardTuple(cards, 2);
        if (cardTuple == null) return null;
        List<Card> result = CardUtils.getMaxNCards(CardUtils.remove(cards, cardTuple.getCards()), 3);

        return new CardCompose(TexasRanking.ONE_PAIR, join(result, cardTuple.getCards()));
    }
}
