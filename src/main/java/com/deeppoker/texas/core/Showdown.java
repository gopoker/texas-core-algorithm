package com.deeppoker.texas.core;

import com.deeppoker.texas.core.algorithm.CardCompose;
import com.google.common.collect.ComparisonChain;

public class Showdown implements Comparable<Showdown> {
    private Player      player;
    private HandsCard   handsCard;
    private CardCompose cardCompose;

    public Showdown(Player player, HandsCard handsCard, CardCompose cardCompose) {
        this.player = player;
        this.handsCard = handsCard;
        this.cardCompose = cardCompose;
    }

    @Override
    public int compareTo(Showdown that) {
        return ComparisonChain.start()
                .compare(that.cardCompose, this.cardCompose)
                .compare(this.player.getIndex(), that.player.getIndex())
                .result();
    }

    public Player getPlayer() {
        return player;
    }

    public HandsCard getHandsCard() {
        return handsCard;
    }

    public CardCompose getCardCompose() {
        return cardCompose;
    }

    @Override
    public String toString() {
        return "Showdown{" +
                "player=" + player +
                ", handsCard=" + handsCard +
                ", cardCompose=" + cardCompose +
                '}';
    }
}

