package com.deeppoker.texas.core.algorithm.comparator;

import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.card.CardTuple;

import java.util.Comparator;
import java.util.List;

import static com.deeppoker.texas.core.card.CardUtils.getCardTuple;
import static com.deeppoker.texas.core.card.CardUtils.remove;

public class TwoPairComparator extends OnePairComparator implements Comparator<List<Card>> {

    @Override
    public int compare(List<Card> cards1, List<Card> cards2) {
        CardTuple cardTuple1 = highPair(cards1);
        CardTuple cardTuple2 = highPair(cards2);
        int result = cardTuple1.getRanking() - cardTuple2.getRanking();
        if (result != 0) return result;

        return super.compare(remove(cards1, cardTuple1.getCards()), remove(cards2, cardTuple2.getCards()));
    }


    private CardTuple highPair(List<Card> cards) {
        CardTuple cardTuple1 = getCardTuple(cards, 2);
        CardTuple cardTuple2 = getCardTuple(remove(cards, cardTuple1.getCards()), 2);
        if (cardTuple1.getRanking() > cardTuple2.getRanking()) return cardTuple1;
        return cardTuple2;
    }
}