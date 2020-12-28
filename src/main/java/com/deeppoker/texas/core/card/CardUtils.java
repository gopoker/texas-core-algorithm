package com.deeppoker.texas.core.card;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

public class CardUtils {


    public static CardTuple getCardTuple(List<Card> cards, int targetTupleSize) {
        Map<Integer, List<Card>> map = cards.stream().collect(Collectors.groupingBy(Card::getRanking));
        Optional<List<Card>> target = map.values().stream().filter(cardList -> cardList.size() == targetTupleSize).findAny();
        return target.map(CardTuple::new).orElse(null);
    }

    public static List<Card> remove(List<Card> source, List<Card> remove) {
        List<Card> list = Lists.newArrayList(source);
        list.removeAll(remove);
        return list;
    }

    public static Card getMaxRanking(List<Card> cards, List<Card> exclude) {
        if (exclude != null) {
            cards = remove(cards, exclude);
        }
        if (cards.isEmpty()) return null;
        Collections.sort(cards);
        return cards.get(cards.size() - 1);
    }


    public static CardTuple getMaxRanking(List<CardTuple> cardTuples) {
        cardTuples.sort(Comparator.reverseOrder());
        return cardTuples.get(0);
    }


    public static List<Card> getMaxNCards(List<Card> cards, int N) {
        if (cards.size() < N) return null;
        if (cards.size() == N) return cards;
        Collections.sort(cards);
        return cards.subList(cards.size() - N, cards.size());
    }
}
