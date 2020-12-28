package com.deeppoker.texas.core.algorithm.judge;

import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.algorithm.CardCompose;
import com.deeppoker.texas.core.algorithm.CardComposeJudge;
import com.deeppoker.texas.core.TexasRanking;

import java.util.List;

public class StraightFlushJudge implements CardComposeJudge {
    @Override
    public CardCompose judge(List<Card> cards) {
        List<Card> target = getMax5Sequence(cards);
        if (target != null) {
            List<Card> sameSuitCards = getCardBySuit(cards, target.get(0).getSuit());
            if (sameSuitCards != null && isEquals(target, sameSuitCards)) {
                return new CardCompose(TexasRanking.STRAIGHT_FLUSH, target);
            }
        }
        return null;
    }
}
