package com.deeppoker.texas.core.algorithm.comparator;

import com.deeppoker.texas.core.card.Card;
import com.google.common.base.Preconditions;

import java.util.Comparator;
import java.util.List;

public class HighCardComparator implements Comparator<List<Card>> {

    @Override
    public int compare(List<Card> cards1, List<Card> cards2) {
        Preconditions.checkArgument(cards1.size() == cards2.size());
        if (cards1.size() == 0) return 0;
        cards1.sort(Comparator.reverseOrder());
        cards2.sort(Comparator.reverseOrder());

        for (int i = 0; i <= cards1.size() - 1; i++) {
            Card card1 = cards1.get(i);
            Card card2 = cards2.get(i);
            int result = Integer.compare(card1.getRanking(), card2.getRanking());
            if (result == 0) continue;
            return result;
        }
        return 0;
    }
}
