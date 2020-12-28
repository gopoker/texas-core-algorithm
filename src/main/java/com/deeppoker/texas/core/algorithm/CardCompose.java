package com.deeppoker.texas.core.algorithm;

import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.TexasRanking;
import com.google.common.base.Preconditions;

import java.util.Collections;
import java.util.List;

public class CardCompose implements Comparable<CardCompose> {
    private static CardComposeComparator cardComposeComparator = new CardComposeComparator();
    private        TexasRanking          ranking;
    private        List<Card>            compose;

    public CardCompose(TexasRanking ranking, List<Card> compose) {
        this.ranking = ranking;
        this.compose = compose;
        Collections.sort(this.compose);
        Preconditions.checkArgument(compose.size() == 5);
    }

    public TexasRanking getRanking() {
        return ranking;
    }

    public List<Card> getCompose() {
        return compose;
    }

    @Override
    public String toString() {
        return ranking.name + ":" + compose.toString();
    }


    @Override
    public int compareTo(CardCompose that) {
        return cardComposeComparator.compare(this, that);
    }
}
