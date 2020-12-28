package com.deeppoker.texas.core.algorithm;

import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.TexasRanking;
import com.deeppoker.texas.core.algorithm.comparator.*;
import com.google.common.collect.Maps;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CardComposeComparator implements Comparator<CardCompose> {

    private Map<TexasRanking, Comparator<List<Card>>> map = Maps.newHashMap();

    public CardComposeComparator() {
        map.put(TexasRanking.HIGH_CARD, new HighCardComparator());
        map.put(TexasRanking.ONE_PAIR, new OnePairComparator());
        map.put(TexasRanking.TWO_PAIR, new TwoPairComparator());
        map.put(TexasRanking.THREE_OF_A_KIND, new ThreeOfAKindComparator());
        map.put(TexasRanking.STRAIGHT, map.get(TexasRanking.HIGH_CARD));
        map.put(TexasRanking.FLUSH, map.get(TexasRanking.HIGH_CARD));
        map.put(TexasRanking.FULL_HOUSE, new FullHouseComparator());
        map.put(TexasRanking.FOUR_OF_A_KIND, map.get(TexasRanking.HIGH_CARD));
        map.put(TexasRanking.STRAIGHT_FLUSH, map.get(TexasRanking.HIGH_CARD));
        map.put(TexasRanking.ROYAL_FLUSH, map.get(TexasRanking.HIGH_CARD));
    }

    @Override
    public int compare(CardCompose compose1, CardCompose compose2) {
        int result = compose1.getRanking().ordinal() - compose2.getRanking().ordinal();
        if (result != 0) return result;
        Comparator<List<Card>> comparator = map.get(compose1.getRanking());
        return comparator.compare(compose1.getCompose(), compose2.getCompose());
    }
}
