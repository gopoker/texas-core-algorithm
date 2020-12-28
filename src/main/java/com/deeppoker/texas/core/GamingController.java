package com.deeppoker.texas.core;

import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.playing.BetPot;
import com.deeppoker.texas.core.playing.BettingRounds;
import com.deeppoker.texas.core.playing.PlayerActions;
import com.deeppoker.texas.core.playing.PlayerQueue;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GamingController {
    private Deck                   deck         = new Deck();
    private Map<Player, HandsCard> handsCardMap = Maps.newHashMap();
    private List<Card>             board        = Lists.newArrayList();
    private BetPot                 betPot       = new BetPot();
    private Scoreboard             scoreboard;
    private List<Player>           players;
    private BettingRounds          bettingRounds;
    private int                    blinds;
    private GameResult             gameResult;
    private long                   lastActionTime;

    public GamingController(List<Player> players, int blinds) {
        this.players = players;
        this.bettingRounds = new BettingRounds(this.players, betPot, blinds);
        scoreboard = new Scoreboard(players);
        this.blinds = blinds;
    }


    public void deal() {
        players.forEach(p -> handsCardMap.put(p, new HandsCard(deck.pop(), deck.pop())));
    }


    private void dealBoard(int card) {
        deck.pop();
        for (int i = 0; i < card; i++) {
            board.add(deck.pop());
        }
    }

    public TexasRound round() {
        return bettingRounds.getCurrent().getRound();
    }

    public int betCall() {
        return betPot.callBet(turn());
    }


    public Player turn() {
        return bettingRounds.getPlayerQueue().peek();
    }

    public boolean gameOver() {
        return bettingRounds.getCurrent().gameOver();
    }

    private List<Player> surviving() {
        PlayerQueue playerQueue = bettingRounds.getCurrent().playerQueue;
        List<Player> players = Lists.newArrayList(playerQueue.getPlaying());
        players.addAll(playerQueue.getAllin());

        return players;
    }

    //下盲注
    public void betBlind() {
        Player player = this.turn();
        bettingRounds.getCurrent().betBlind(player, blinds);
        player = this.turn();
        bettingRounds.getCurrent().betBlind(player, blinds << 1);
    }


    public int betAnte(int ante) {
        if (ante > 0) {
            return bettingRounds.getCurrent().betAnte(ante);
        }
        return 0;
    }


    public int play(Player player, TexasAction action, int bet) {
        Preconditions.checkState(player.equals(turn()));
        switch (action) {
            case BET:
                bet = bettingRounds.getCurrent().bet(player, bet);
                break;
            case CALL:
                bet = bettingRounds.getCurrent().call(player);
                break;
            case FOLD:
                bet = bettingRounds.getCurrent().fold(player);
                break;
            case ALLIN:
                bet = bettingRounds.getCurrent().allin(player);
                break;
            case CHECK:
                bet = bettingRounds.getCurrent().check(player);
                break;
            case RAISE:
            case RE_RAISE:
                bet = bettingRounds.getCurrent().raise(player, bet);
                break;
        }


        if (bettingRounds.getCurrent().nextRound()) {
            switch (bettingRounds.getCurrent().getRound()) {
                case PRE_FLOP:
                    dealBoard(3);
                    bettingRounds.nextRound();
                    return bet;
                case FLOP:
                case TURN:
                    dealBoard(1);
                    bettingRounds.nextRound();
                    return bet;
                case RIVER:
                    // 在river 阶段且资金池水平，则是游戏结束
                    // showdown(surviving());
                    break;
            }
        }


        if (gameOver()) {
            List<Player> players = surviving();
            gameResult = new GameResult(handsCardMap, betPot, players, scoreboard, board, deck);
        }
        lastActionTime = System.currentTimeMillis();

        return bet;
    }

    public Snapshot getSnapshot() {
        Snapshot snapshot = new Snapshot();
        snapshot.setPlayers(players.stream().map(Player::getId).collect(Collectors.toList()));
        snapshot.setCurrent(turn().getId());
        snapshot.setFold(players.stream().filter(Player::isFold).map(Player::getId).collect(Collectors.toList()));
        snapshot.setAllin(players.stream().filter(Player::isAllin).map(Player::getId).collect(Collectors.toList()));
        snapshot.setTexasRound(bettingRounds.getCurrent().round);
        snapshot.setBankRoll(players.stream().map(p -> p.getBankRoll().getValue()).collect(Collectors.toList()));
        snapshot.setBoard(getBoard());
        snapshot.setLastActionTime(lastActionTime);
        snapshot.setPot(betPot.potSummary());
        return snapshot;
    }


    public HandsCard getHandsCard(Player player) {
        return handsCardMap.get(player);
    }

    public List<Card> getBoard() {
        return board;
    }


    public PlayerActions actions() {
        return bettingRounds.getActions();
    }

    public GameResult getGameResult() {
        return gameResult;
    }
}
