package com.deeppoker.texas.core.algorithm.judge;

import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.Poker;
import com.deeppoker.texas.core.algorithm.CardCompose;
import com.deeppoker.texas.core.algorithm.CardComposeJudge;
import com.deeppoker.texas.core.TexasRanking;
import com.deeppoker.texas.core.card.CardUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlushJudge implements CardComposeJudge {
    @Override
    public CardCompose judge(List<Card> cards) {
        Map<Poker.Suit, List<Card>> map = cards.stream().collect(Collectors.groupingBy(Card::getSuit));
        Optional<List<Card>> target = map.values().stream().filter(v -> v.size() >= 5).findFirst();
        if (target.isPresent()) {
            List<Card> flush = CardUtils.getMaxNCards(target.get(), 5);
            return new CardCompose(TexasRanking.FLUSH, flush);
        }
        return null;
    }
}
