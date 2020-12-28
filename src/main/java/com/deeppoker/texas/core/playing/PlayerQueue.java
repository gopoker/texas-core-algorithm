package com.deeppoker.texas.core.playing;

import com.deeppoker.texas.core.Player;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class PlayerQueue {
    private List<Player>       playOrder;
    private LinkedList<Player> playing       = Lists.newLinkedList();
    private List<Player>       fold          = Lists.newArrayList();
    private List<Player>       allin         = Lists.newArrayList();
    private Set<Player>        calledInRound = Sets.newHashSet();

    public PlayerQueue(List<Player> players) {
        playing.addAll(players);
        playing.offerLast(playing.pollFirst());
        this.playOrder = Lists.newArrayList(playing);
    }

    public Player peek() {
        return playing.peekFirst();
    }

    //玩家已经说话
    public void called(boolean called) {
        Player player = playing.pollFirst();
        playing.offerLast(player);
        if (called) {
            calledInRound.add(player);
        }
    }

    public void fold() {
        fold.add(playing.pollFirst());
    }

    public void allin() {
        allin.add(playing.pollFirst());
    }

    public LinkedList<Player> getPlaying() {
        return playing;
    }

    public List<Player> getFold() {
        return fold;
    }

    public List<Player> getAllin() {
        return allin;
    }


    //重置下注顺序
    public void reset() {
        Preconditions.checkState(playing.size() > 1);
        for (Player player : playOrder) {
            if (playing.contains(player)) {
                while (!player.equals(playing.peekFirst())) {
                    playing.offerLast(playing.pollFirst());
                }
                break;
            }
        }
        calledInRound.clear();
    }

    //本轮中未说话的玩家
    public List<Player> unCallPlayer() {
        List<Player> players = Lists.newArrayList(playing);
        players.removeAll(calledInRound);
        return players;
    }
}
