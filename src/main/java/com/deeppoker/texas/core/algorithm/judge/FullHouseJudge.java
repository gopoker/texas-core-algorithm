package com.deeppoker.texas.core.algorithm.judge;

import com.deeppoker.texas.core.TexasRanking;
import com.deeppoker.texas.core.algorithm.CardCompose;
import com.deeppoker.texas.core.algorithm.CardComposeJudge;
import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.card.CardTuple;
import com.deeppoker.texas.core.card.CardUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FullHouseJudge implements CardComposeJudge {
    @Override
    public CardCompose judge(List<Card> cards) {
        List<CardTuple> cardTuples = Lists.newArrayList();
        CardTuple target = CardUtils.getCardTuple(cards, 3);
        if (target == null) return null;
        List<Card> remove = Lists.newArrayList();
        while (target != null) {
            cardTuples.add(target);
            remove.addAll(target.getCards());
            target = CardUtils.getCardTuple(CardUtils.remove(cards, remove), 3);
        }
        target = CardUtils.getMaxRanking(cardTuples);
        CardTuple cardTuple = getMaxPairOf4Cards(CardUtils.remove(cards, target.getCards()));
        if (cardTuple != null) {
            return new CardCompose(TexasRanking.FULL_HOUSE, join(target.getCards(), cardTuple.getCards()));
        }
        return null;
    }


    private CardTuple getMaxPairOf4Cards(List<Card> cards) {
        Preconditions.checkArgument(cards.size() <= 4);
        Map<Integer, List<Card>> map = cards.stream().collect(Collectors.groupingBy(Card::getRanking));
        List<List<Card>> cardsList = map.values().stream().filter(c -> c.size() >= 2).collect(Collectors.toList());
        if (cardsList.size() == 0) return null;
        if (cardsList.size() == 1) return new CardTuple(cardsList.get(0).subList(0, 2));
        if (cardsList.size() == 2) {
            Card card1 = cardsList.get(0).get(0);
            Card card2 = cardsList.get(1).get(0);
            if (card1.getRanking() > card2.getRanking()) {
                return new CardTuple(cardsList.get(0).subList(0, 2));
            } else {
                return new CardTuple(cardsList.get(1).subList(0, 2));
            }
        }
        throw new IllegalStateException("Found " + cardsList.size() + "pair in " + cards.size() + " cards.");
    }

    public static void main(String[] args) {
        List<Card> cards = Lists.newArrayList(new Card((byte) 0x02), new Card((byte) 0x12), new Card((byte) 0x22), new Card((byte) 0x04));
        FullHouseJudge fullHouseJudge = new FullHouseJudge();
        System.out.println(fullHouseJudge.getMaxPairOf4Cards(cards));
    }
}
