package com.deeppoker.texas.core;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Player {
    private long     id;
    private BankRoll bankRoll;
    private boolean  fold, allin;
    private int index;

    public Player(long id, int bankRoll, int index) {
        this.id = id;
        this.bankRoll = new BankRoll(new AtomicInteger(bankRoll));
        this.index = index;
    }

    public BankRoll getBankRoll() {
        return bankRoll;
    }

    public boolean isFold() {
        return fold;
    }

    public void setFold() {
        this.fold = true;
    }

    public boolean isAllin() {
        return allin;
    }

    public void setAllin() {
        this.allin = true;
    }

    public int getIndex() {
        return index;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Player[" + id + "," + bankRoll.getValue() + "]";
    }
}
