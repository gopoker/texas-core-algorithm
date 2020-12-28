package com.deeppoker.texas.core.algorithm;


import com.deeppoker.texas.core.algorithm.judge.RoyalFlushJudge;
import com.deeppoker.texas.core.card.Card;
import com.google.common.collect.Lists;

import java.util.List;

public class CardComposeJudgeTest {

    public static void main(String[] args) {

        List<Card> cards = Lists.newArrayList(
                new Card((byte) 0x02),
                new Card((byte) 0x03),
                new Card((byte) 0x14),
                new Card((byte) 0x05),
                new Card((byte) 0x06),
                new Card((byte) 0x07),
                new Card((byte) 0x08));

        CardComposeJudge cardComposeJudge = new RoyalFlushJudge();

        System.out.println(cardComposeJudge.getMax5Sequence(cards));
    }

}