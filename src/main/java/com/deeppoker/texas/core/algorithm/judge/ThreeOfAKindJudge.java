package com.deeppoker.texas.core.algorithm.judge;

import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.algorithm.CardCompose;
import com.deeppoker.texas.core.algorithm.CardComposeJudge;
import com.deeppoker.texas.core.TexasRanking;
import com.deeppoker.texas.core.card.CardTuple;
import com.deeppoker.texas.core.card.CardUtils;
import com.google.common.collect.Lists;

import java.util.List;

public class ThreeOfAKindJudge implements CardComposeJudge {
    @Override
    public CardCompose judge(List<Card> cards) {
        CardTuple target = CardUtils.getCardTuple(cards, 3);
        if (target == null) return null;
        Card cardOne = CardUtils.getMaxRanking(cards, target.getCards());
        Card cardTwo = CardUtils.getMaxRanking(cards, join(target.getCards(), Lists.newArrayList(cardOne)));
        return new CardCompose(TexasRanking.THREE_OF_A_KIND, join(target.getCards(), Lists.newArrayList(cardOne, cardTwo)));
    }
}
