package com.deeppoker.texas.core.algorithm.judge;

import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.algorithm.CardCompose;
import com.deeppoker.texas.core.algorithm.CardComposeJudge;
import com.deeppoker.texas.core.TexasRanking;
import com.deeppoker.texas.core.card.CardUtils;

import java.util.List;

public class HighCardJudge implements CardComposeJudge {
    @Override
    public CardCompose judge(List<Card> cards) {
        return new CardCompose(TexasRanking.HIGH_CARD, CardUtils.getMaxNCards(cards, 5));
    }
}
