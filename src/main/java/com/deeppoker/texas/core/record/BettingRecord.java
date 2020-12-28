package com.deeppoker.texas.core.record;

import com.deeppoker.texas.core.TexasAction;
import com.deeppoker.texas.core.TexasRound;

public class BettingRecord {
    public TexasRound  round;
    public long        uid;
    public TexasAction action;
    public int         value;
}
