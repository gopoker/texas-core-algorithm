package com.deeppoker.texas.core.record;

import com.deeppoker.texas.core.GameResult;
import com.deeppoker.texas.core.HandsCard;
import com.deeppoker.texas.core.Player;
import com.deeppoker.texas.core.Scoreboard;
import com.deeppoker.texas.core.card.Card;
import com.google.common.collect.Lists;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class GameRecord {
    public  long                         roomId;
    public  int                          gameId;
    public  long                         playTime;
    private List<Long>                   playerIds = Lists.newArrayList();
    private List<Integer>                bankRolls = Lists.newArrayList();
    private int                          ante;
    private int                          blinds;
    private List<HandsCard>              handsCards;
    private List<BettingRecord>          bettingRecords;
    private List<Integer>                result;
    private List<GameResult.ResultRound> resultRounds;
    private List<Card>                   board;
    private List<Integer>                scoreboard;


    public GameRecord(long roomId, int gameId, List<Player> players, int ante, int blinds) {
        this.roomId = roomId;
        this.gameId = gameId;
        this.playTime = Instant.now().getEpochSecond();
        for (Player player : players) {
            playerIds.add(player.getId());
            bankRolls.add(player.getBankRoll().getValue());
        }
        this.ante = ante;
        this.blinds = blinds;
    }

    public void addHandsCards(Map<Player, HandsCard> handsCardMap) {
        for (long id : playerIds) {
            handsCardMap.keySet().forEach(p -> {
                if (p.getId() == id) {
                    handsCards.add(handsCardMap.get(p));
                }
            });
        }
    }

    public void addScoreboard(Scoreboard scoreboard) {
        for (long id : playerIds) {
            scoreboard.getChips().keySet().forEach(p -> {
                if (p.getId() == id) {
                    this.scoreboard.add(scoreboard.getChips().get(p));
                }
            });
        }
    }

    public void setBoard(List<Card> board) {
        this.board = board;
    }
}
