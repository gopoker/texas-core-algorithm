package com.deeppoker.texas.core;

import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.playing.BetPot;

import java.util.List;

public class RunItTwice {
    private GameResult.ResultRound resultRound1;
    private GameResult.ResultRound resultRound2;
    private List<Card>             board;
    private BetPot                 betPot;
    private List<BetPot>           betPots;


    private void splitPot(BetPot betPot) {
        List<BetPot> betPots = betPot.split();

    }
}
