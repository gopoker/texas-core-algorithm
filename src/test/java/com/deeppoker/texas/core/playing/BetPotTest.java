package com.deeppoker.texas.core.playing;

import com.deeppoker.texas.core.Player;

public class BetPotTest {

    public void test() {
        Player player = new Player(1, 100, 1);
        Player player1 = new Player(1, 100, 1);
        Player player2 = new Player(1, 100, 1);
        Player player3 = new Player(1, 100, 1);

        BetPot betPot = new BetPot();
        betPot.getPots().current().put(player, new Pot(20));
        betPot.getPots().current().put(player, new Pot(30));
    }
}
