package com.deeppoker.texas.core.playing;

import com.deeppoker.texas.core.TexasAction;

public class AvailableAction {
    public final TexasAction action;
    public final int         minBet;

    public AvailableAction(TexasAction action, int minBet) {
        this.action = action;
        this.minBet = minBet;
    }

    @Override
    public String toString() {
        return "AvailableAction{" +
                "action=" + action +
                ", minBet=" + minBet +
                '}';
    }
}