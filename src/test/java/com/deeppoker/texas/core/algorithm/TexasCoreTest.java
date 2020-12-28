package com.deeppoker.texas.core.algorithm;


import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.Deck;
import com.deeppoker.texas.core.HandsCard;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TexasCoreTest {

    private Deck      deck      = new Deck();
    private TexasCore texasCore = new TexasCore();


    @Test
    public void testPk() throws IOException {
        FileWriter fileWriter = new FileWriter(new File("/opt/logs/poker.txt"));
        BufferedWriter bf = new BufferedWriter(fileWriter);
        for (int i = 0; i < 10000; i++) {
            Deck deck = new Deck();
            HandsCard handsCard1 = new HandsCard(deck.pop(), deck.pop());
            HandsCard handsCard2 = new HandsCard(deck.pop(), deck.pop());
            List<Card> tableCard = Lists.newArrayList(deck.pop(), deck.pop(), deck.pop(), deck.pop(), deck.pop());
            CardCompose cardCompose1 = texasCore.getCardCompose(handsCard1, tableCard);
            CardCompose cardCompose2 = texasCore.getCardCompose(handsCard2, tableCard);
            print(tableCard, handsCard1, handsCard2, cardCompose1, cardCompose2, bf);
        }
        bf.close();
    }

    private void print(List<Card> tableCard,
                       HandsCard handsCard1,
                       HandsCard handsCard2,
                       CardCompose cardCompose1,
                       CardCompose cardCompose2,
                       BufferedWriter bf
    ) throws IOException {
        int result = cardCompose1.compareTo(cardCompose2);

        if (result == 0) {
            String message = tableCard.toString() + handsCard1.toString() + "---> " + cardCompose1 + " 平";
            System.out.println(message);
            bf.write(message);
            bf.newLine();

            message = tableCard.toString() + handsCard2.toString() + "---> " + cardCompose2 + " 平";
            System.out.println(message);
            bf.write(message);
            bf.newLine();
        }

        if (result < 0) {

            String message = tableCard.toString() + handsCard1.toString() + "---> " + cardCompose1 + " 输";
            System.out.println(message);
            bf.write(message);
            bf.newLine();

            message = tableCard.toString() + handsCard2.toString() + "---> " + cardCompose2 + " 赢";
            System.out.println(message);
            bf.write(message);
            bf.newLine();
        }

        if (result > 0) {


            String message = tableCard.toString() + handsCard1.toString() + "---> " + cardCompose1 + " 赢";
            System.out.println(message);
            bf.write(message);
            bf.newLine();

            message = tableCard.toString() + handsCard2.toString() + "---> " + cardCompose2 + " 输";
            System.out.println(message);
            bf.write(message);
            bf.newLine();
        }
        System.out.println();
        bf.newLine();
    }
}