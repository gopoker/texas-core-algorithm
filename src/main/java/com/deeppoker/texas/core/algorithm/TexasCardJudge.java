package com.deeppoker.texas.core.algorithm;

import com.deeppoker.texas.core.algorithm.judge.*;
import com.deeppoker.texas.core.card.Card;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;

public class TexasCardJudge implements CardComposeJudge {
    private List<CardComposeJudge> judges = Lists.newArrayList();

    public TexasCardJudge() {
        judges.add(new RoyalFlushJudge());
        judges.add(new StraightFlushJudge());
        judges.add(new FourOfAKindJudge());
        judges.add(new FullHouseJudge());
        judges.add(new FlushJudge());
        judges.add(new StraightJudge());
        judges.add(new ThreeOfAKindJudge());
        judges.add(new TwoPairJudge());
        judges.add(new OnePairJudge());
        judges.add(new HighCardJudge());
    }


    @Override
    public CardCompose judge(List<Card> cards) {
        Preconditions.checkArgument(cards.size() >= 5);
        for (CardComposeJudge cardComposeJudge : judges) {
            CardCompose cardCompose = cardComposeJudge.judge(cards);
            if (cardCompose != null) return cardCompose;
        }
        throw new IllegalStateException("Code should not run here.");
    }
}
