package com.deeppoker.texas.core.playing;

import com.deeppoker.texas.core.Player;
import com.deeppoker.texas.core.TexasAction;
import com.deeppoker.texas.core.TexasRound;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

public class BettingRounds {
    private List<BettingRound> bettingRounds = Lists.newArrayList();
    private BetPot             betPot;
    private PlayerQueue        playerQueue;
    private BettingRound       current;
    private int                blinds;


    public BettingRounds(List<Player> players, BetPot betPot, int blinds) {
        playerQueue = new PlayerQueue(players);
        this.betPot = betPot;
        current = new BettingRound(TexasRound.PRE_FLOP, betPot, playerQueue);
        bettingRounds.add(current);
        this.blinds = blinds;
    }

    public void nextRound() {
        Preconditions.checkArgument(!current.gameOver());
        current = new BettingRound(current.round.next(), betPot, playerQueue);
        bettingRounds.add(current);
        betPot.getPots().makeSidePot();
    }

    public BettingRound getCurrent() {
        return current;
    }

    public PlayerQueue getPlayerQueue() {
        return playerQueue;
    }

    public PlayerActions getActions() {
        Player player = current.playerQueue.peek();
        PlayerActions playerActions = new PlayerActions(player);
        playerActions.addAction(TexasAction.FOLD, 0);
        playerActions.addAction(TexasAction.ALLIN, player.getBankRoll().getValue());

        int callBet = betPot.callBet(player);

        if (player.getBankRoll().getValue() > callBet && callBet > 0) {
            playerActions.addAction(TexasAction.CALL, callBet);
        }

        int raise = Math.max(callBet + current.getLastRaise(), callBet + (blinds << 1));
        if (player.getBankRoll().getValue() > raise && !player.equals(current.getLastRaisePlayer()) && current.getLastRaise() >= 0) {
            playerActions.addAction(TexasAction.RAISE, raise);
        }

        if (current.playerQueue.getPlaying().size() == 1) {
            if (player.getBankRoll().getValue() > callBet) {
                playerActions.remove(TexasAction.ALLIN); //只剩老子了，钱能跟上就不用allin了
            }
        }

        Map<Player, Integer> betInRound = current.getBetInRound();
        if (betInRound.values().stream().mapToInt(v -> v).sum() == 0) {
            playerActions.addAction(TexasAction.CHECK, 0);
            playerActions.remove(TexasAction.FOLD); //能CHECK的情况下，就不要弃牌了
            if (player.getBankRoll().getValue() > blinds << 1) {
                playerActions.addAction(TexasAction.BET, blinds << 1);
            }
        }

        return playerActions;
    }
}
