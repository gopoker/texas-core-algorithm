package com.deeppoker.texas.core;

import com.google.common.base.Preconditions;

import java.util.concurrent.atomic.AtomicInteger;

public class BankRoll {
    private AtomicInteger value;

    public BankRoll(AtomicInteger value) {
        this.value = value;
    }

    public int bet(int number) {
        Preconditions.checkArgument(getValue() >= number);
        value.addAndGet(-number);
        return number;
    }

    public int win(int number) {
        return value.addAndGet(number);
    }

    public int getValue() {
        return value.intValue();
    }


    public int allIn() {
        return bet(getValue());
    }
}
