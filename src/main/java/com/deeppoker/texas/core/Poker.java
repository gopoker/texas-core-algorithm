package com.deeppoker.texas.core;

import com.deeppoker.texas.core.card.Card;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class Poker {
    public final static byte[] cardValue = new byte[]{
            //方块
            0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E,
            //梅花
            0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E,
            //红桃
            0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2A, 0x2B, 0x2C, 0x2D, 0x2E,
            //黑桃
            0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3A, 0x3B, 0x3C, 0x3D, 0x3E,
    };

    public static List<Card>              cards   = Lists.newArrayList();
    public static Map<Integer, Character> cardMap = Maps.newHashMap();

    static {
        cardMap.put(0x02 & 0xF, '2');
        cardMap.put(0x03 & 0xF, '3');
        cardMap.put(0x04 & 0xF, '4');
        cardMap.put(0x05 & 0xF, '5');
        cardMap.put(0x06 & 0xF, '6');
        cardMap.put(0x07 & 0xF, '7');
        cardMap.put(0x08 & 0xF, '8');
        cardMap.put(0x09 & 0xF, '9');
        cardMap.put(0x0A & 0xF, 'T');
        cardMap.put(0x0B & 0xF, 'J');
        cardMap.put(0x0C & 0xF, 'Q');
        cardMap.put(0x0D & 0xF, 'K');
        cardMap.put(0x0E & 0xF, 'A');

        for (byte c : cardValue) {
            cards.add(new Card(c));
        }

    }

    public enum Suit {
        DIAMONDS(0x02 >> 4, 'd', '♦', "方块"),
        CLUBS(0x12 >> 4, 'c', '♣', "梅花"),
        HEARTS(0x22 >> 4, 'h', '♥', "红桃"),
        SPADES(0x32 >> 4, 's', '♠', "黑桃");

        public int    value;
        public char   symbol;
        public char   code;
        public String name;

        Suit(int value, char code, char symbol, String name) {
            this.value = value;
            this.symbol = symbol;
            this.name = name;
            this.code = code;
        }
    }
}