package com.deeppoker.texas.core.algorithm.comparator;

import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.card.CardTuple;

import java.util.Comparator;
import java.util.List;

import static com.deeppoker.texas.core.card.CardUtils.getCardTuple;
import static com.deeppoker.texas.core.card.CardUtils.remove;

public class FullHouseComparator implements Comparator<List<Card>> {
    @Override
    public int compare(List<Card> cards1, List<Card> cards2) {
        CardTuple cardTuple1 = getCardTuple(cards1, 3);
        CardTuple cardTuple2 = getCardTuple(cards2, 3);
        int result = cardTuple1.getRanking() - cardTuple2.getRanking();
        if (result != 0) return result;

        cardTuple1 = new CardTuple(remove(cards1, cardTuple1.getCards()));
        cardTuple2 = new CardTuple(remove(cards2, cardTuple2.getCards()));
        return cardTuple1.getRanking() - cardTuple2.getRanking();
    }
}
