package com.deeppoker.texas.core.playing;

import com.deeppoker.texas.core.Player;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Random;

public class BettingRoundsTest {

    private Random random = new Random();

    @Test
    public void test() {
        List<Player> players = Lists.newArrayList();
        for (int i = 1; i <= 5; i++) {
            Player player = new Player(i, random.nextInt(50) + 10, i);
            players.add(player);
        }

        BettingRounds bettingRounds = new BettingRounds(players, new BetPot(), 1);

        bettingRounds.getCurrent().betBlind(bettingRounds.getCurrent().playerQueue.peek(), 1);   //2
        bettingRounds.getCurrent().betBlind(bettingRounds.getCurrent().playerQueue.peek(), 2);   //3
        bettingRounds.getCurrent().bet(bettingRounds.getCurrent().playerQueue.peek(), 2);         //4
        bettingRounds.getCurrent().fold(bettingRounds.getCurrent().playerQueue.peek());               //5
        bettingRounds.getCurrent().call(bettingRounds.getCurrent().playerQueue.peek());               //1
        bettingRounds.getCurrent().call(bettingRounds.getCurrent().playerQueue.peek());               //2
        bettingRounds.getCurrent().raise(bettingRounds.getCurrent().playerQueue.peek(), 4);      //3

        System.out.println(bettingRounds.getActions());

    }
}
