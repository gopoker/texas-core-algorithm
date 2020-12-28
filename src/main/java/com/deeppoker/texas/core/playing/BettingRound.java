package com.deeppoker.texas.core.playing;

import com.deeppoker.texas.core.Player;
import com.deeppoker.texas.core.TexasRound;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.util.Map;

public class BettingRound {
    public  TexasRound           round;
    public  BetPot               betPot;
    public  PlayerQueue          playerQueue;
    private Map<Player, Integer> betInRound = Maps.newHashMap();
    private int                  lastRaise;
    private Player               lastRaisePlayer;


    public BettingRound(TexasRound round, BetPot betPot, PlayerQueue playerQueue) {
        this.round = round;
        this.betPot = betPot;
        this.playerQueue = playerQueue;
        this.playerQueue.reset();
    }

    public void betBlind(Player player, int blind) {
        Preconditions.checkState(player.equals(playerQueue.peek()));
        betPot.bet(player, player.getBankRoll().bet(blind), false);
        playerQueue.called(false);
        betInRound.merge(player, blind, Integer::sum);
    }


    public int betAnte(int ante) {
        int sum = 0;
        for (Player player : playerQueue.getPlaying()) {
            betPot.bet(player, player.getBankRoll().bet(ante), false);
            betInRound.merge(player, ante, Integer::sum);
            sum += ante;
        }
        return sum;
    }


    //下注
    public int bet(Player player, int bet) {
        Preconditions.checkState(player.equals(playerQueue.peek()));
        betPot.bet(player, player.getBankRoll().bet(bet), false);
        playerQueue.called(true);
        betInRound.merge(player, bet, Integer::sum);
        if (lastRaise == 0) lastRaise = bet;
        return bet;
    }

    //让牌
    public int check(Player player) {
        bet(player, 0);
        return 0;
    }

    //跟注
    public int call(Player player) {
        return bet(player, betPot.callBet(player));
    }

    //加注
    public int raise(Player player, int raise) {
        lastRaise = raise - betPot.callBet(player);
        lastRaisePlayer = player;
        return bet(player, raise);
    }

    //全压
    public int allin(Player player) {
        Preconditions.checkState(player.equals(playerQueue.peek()));
        playerQueue.allin();
        int allin = player.getBankRoll().getValue();
        player.setAllin();
        int callBet = betPot.callBet(player);
        if (lastRaise >= 0 && allin >= lastRaise + callBet) {
            lastRaise = allin - callBet;
            lastRaisePlayer = player;
        } else if (allin > callBet) { //如果当前玩家的allin值比跟注值要大，但是小于应该raise的值；对应 1，2，4，5 这种情况 raise值为6
            lastRaise = -1; //将此值标记为-1 代表本轮将不能有玩家raise了；
            lastRaisePlayer = null;

        }
        betInRound.merge(player, allin, Integer::sum);
        return betPot.bet(player, player.getBankRoll().allIn(), true);
    }

    public int fold(Player player) {
        Preconditions.checkState(player.equals(playerQueue.peek()));
        player.setFold();
        playerQueue.fold();
        return 0;
    }


    /*
        进入下一轮的条件:
        1 本局所有玩家都已经说话(小小盲和大盲不算说话)；
        2 当前局为非RIVER局；
        3 资金池水平
        4 activePlayer玩家数 > 1
     */
    public boolean nextRound() {
        if (round == TexasRound.RIVER) {
            return false;
        }
        return playerQueue.unCallPlayer().size() == 0
                && playerQueue.getPlaying().size() > 1
                && betPot.watermark();
    }

    /*
        游戏结束条件:
        1 彩池已经水平（即非弃牌、allin玩家下注的筹码一样多）；
        2 本局中，active玩家至少有1个人说过话；
        3 当前局中，玩家数小于2
        4 RIVER局中，所有玩家都已经说话；彩池已经水平；
     */
    public boolean gameOver() {
        if (round == TexasRound.PRE_FLOP) {
            return playerQueue.getPlaying().size() < 2 && betPot.watermark();
        }

        return playerQueue.unCallPlayer().size() <= 1 &&
                (round == TexasRound.RIVER || playerQueue.getPlaying().size() < 2)
                && betPot.watermark();
    }

    public TexasRound getRound() {
        return round;
    }

    public Map<Player, Integer> getBetInRound() {
        return betInRound;
    }

    public int getLastRaise() {
        return lastRaise;
    }

    public Player getLastRaisePlayer() {
        return lastRaisePlayer;
    }
}
