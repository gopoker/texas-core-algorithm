package com.deeppoker.texas.core.playing;

import com.deeppoker.texas.core.Player;
import org.junit.Test;

public class PotsTest {

    @Test
    public void test() {
        Player player1 = new Player(1, 0, 1);
        Player player2 = new Player(2, 0, 2);
        Player player3 = new Player(3, 0, 3);
        Player player4 = new Player(4, 0, 4);


        Pots pots = new Pots();
        // pots.pots.add(Maps.newHashMap());

        pots.current().put(player1, new Pot(100));
        player1.setAllin();

        pots.current().put(player2, new Pot(150));
        player2.setAllin();

        pots.current().put(player3, new Pot(300));
        player3.setAllin();

        // pots.current().put(player4, new Pot(300));

        pots.makeSidePot();

        System.out.println(pots);

    }

}
