package com.deeppoker.texas.core;

import com.deeppoker.texas.core.algorithm.CardCompose;
import com.deeppoker.texas.core.algorithm.TexasCore;
import com.deeppoker.texas.core.card.Card;
import com.deeppoker.texas.core.playing.BetPot;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameResult {
    private Map<Player, HandsCard> handsCard;
    private BetPot                 betPot;
    private List<Player>           surviving;
    private List<ResultRound>      resultRounds = Lists.newArrayList();
    private Scoreboard             scoreboard;
    private List<Card>             board;
    private Deck                   deck;


    public GameResult(Map<Player, HandsCard> handsCard,
                      BetPot betPot, List<Player> surviving,
                      Scoreboard scoreboard,
                      List<Card> board,
                      Deck deck) {
        this.handsCard = handsCard;
        this.betPot = betPot;
        this.surviving = surviving;
        this.scoreboard = scoreboard;
        this.board = board;
        this.deck = deck;
    }

    private ResultRound showdown(List<Card> board) {

        ResultRound resultRound = new ResultRound(board);

        for (Player player : surviving) {
            CardCompose cardCompose = TexasCore.getCardCompose(handsCard.get(player), board);
            resultRound.showdowns.add(new Showdown(player, handsCard.get(player), cardCompose));
        }
        Collections.sort(resultRound.showdowns);
        List<Player> winPlayers = Lists.newArrayList();
        winPlayers.add(resultRound.showdowns.get(0).getPlayer());
        List<List<Player>> winner = Lists.newArrayList();
        winner.add(winPlayers);

        for (int i = 1; i < resultRound.showdowns.size(); i++) {
            if (resultRound.showdowns.get(i).getCardCompose().compareTo(resultRound.showdowns.get(i - 1).getCardCompose()) == 0) {
                winner.get(winner.size() - 1).add(resultRound.showdowns.get(i).getPlayer());
            } else {
                winner.add(Lists.newArrayList(resultRound.showdowns.get(i).getPlayer()));
            }
        }
        resultRound.winner = winner;
        return resultRound;
    }


    public void winnerOne() {
        Preconditions.checkArgument(surviving.size() == 1);
        betPot.take(surviving.get(0));
        ResultRound resultRound = new ResultRound(fillBoard(deck));
        scoreboard.counter();
        this.resultRounds.add(resultRound);
    }

    public void showdown(boolean runItTwice) {
        if (runItTwice) {
            List<BetPot> betPots = this.betPot.split();
            for (BetPot betPot : betPots) {
                ResultRound resultRound = showdown(fillBoard(deck));
                // resultRound.scoreboard.counter();
                betPot.take(resultRound.winner);
                // resultRound.scoreboard.counter();
                resultRounds.add(resultRound);
                Preconditions.checkArgument(betPot.sumBet() == 0);
            }
        } else {
            ResultRound resultRound = showdown(fillBoard(deck));
            betPot.take(resultRound.winner);
            // resultRound.scoreboard.counter();
            resultRounds.add(resultRound);
            Preconditions.checkArgument(betPot.sumBet() == 0);
        }
        scoreboard.counter();
    }

    /**
     * 判断能不能RunItTwice
     * 1 至少有1个allin玩家;
     * 2 公共牌发牌数量少于5;
     * 3 只有2名玩家
     */
    public boolean canRunItTwice() {
        Optional<Player> optionalPlayer = surviving.stream().filter(Player::isAllin).findAny();
        return optionalPlayer.isPresent() && board.size() < 5 && surviving.size() == 2;
    }

    public List<ResultRound> getResultRounds() {
        return resultRounds;
    }

    private List<Card> fillBoard(Deck deck) {
        deck.pop();
        List<Card> board = Lists.newArrayList(this.board);
        int fillCount = 5 - board.size();
        for (int i = 0; i < fillCount; i++) {
            board.add(deck.pop());
        }
        return board;
    }

    public List<Player> getSurviving() {
        return surviving;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public static class ResultRound {
        // public Scoreboard         scoreboard;
        public List<Card>         board;
        public List<Showdown>     showdowns = Lists.newArrayList();
        public List<List<Player>> winner;

        public ResultRound(List<Card> board) {
            //this.scoreboard = scoreboard;
            this.board = board;
        }
    }
}
