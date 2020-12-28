package com.deeppoker.texas.core;

import com.deeppoker.texas.core.card.Card;
import lombok.Data;

import java.util.List;

@Data
public class Snapshot {
    private List<Long>    players;
    private List<Long>    fold;
    private List<Long>    allin;
    private long          current;
    private List<Integer> bankRoll;
    private TexasRound    texasRound;
    private List<Integer> pot;
    private List<Card>    board;
    private long          lastActionTime;
}
