package com.deeppoker.texas.core;

public enum TexasRound {
    PRE_FLOP, FLOP, TURN, RIVER;

    public TexasRound next() {
        switch (this) {
            case PRE_FLOP:
                return FLOP;
            case FLOP:
                return TURN;
            case TURN:
                return RIVER;
            case RIVER:
                return null;
        }
        throw new IllegalStateException();
    }
}
