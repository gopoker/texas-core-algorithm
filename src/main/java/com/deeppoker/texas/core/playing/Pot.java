package com.deeppoker.texas.core.playing;

public class Pot {
    int pot;

    public Pot(int pot) {
        this.pot = pot;
    }

    public Pot betMerge(Pot that) {
        this.pot = this.pot + that.pot;
        return this;
    }


    public Pot cut(int bet) {
        Pot pot = new Pot(this.pot - bet);
        this.pot = bet;
        return pot;
    }

    @Override
    public String toString() {
        return "[" + pot + "]";
    }
}