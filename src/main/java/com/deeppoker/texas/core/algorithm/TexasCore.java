package com.deeppoker.texas.core.algorithm;

import com.deeppoker.texas.core.HandsCard;
import com.deeppoker.texas.core.card.Card;
import com.google.common.collect.Lists;

import java.util.List;

public class TexasCore {
    private static TexasCardJudge texasCardJudge = new TexasCardJudge();

    public static CardCompose getCardCompose(HandsCard handsCard, List<Card> tableCards) {
        return texasCardJudge.judge(merge(handsCard, tableCards));
    }

    private static List<Card> merge(HandsCard handCard, List<Card> cards) {
        List<Card> result = Lists.newArrayList(cards);
        result.add(handCard.cardA);
        result.add(handCard.cardB);
        return result;
    }
}
