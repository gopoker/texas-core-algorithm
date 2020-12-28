package com.deeppoker.texas.core.playing;

import com.deeppoker.texas.core.Player;
import com.deeppoker.texas.core.TexasAction;
import com.google.common.collect.Lists;

import java.util.List;

public class PlayerActions {
    private Player                player;
    private List<AvailableAction> actions = Lists.newArrayList();


    public PlayerActions(Player player) {
        this.player = player;
    }


    public void addAction(TexasAction action, int minBet) {
        actions.add(new AvailableAction(action, minBet));
    }

    public void remove(TexasAction action) {
        actions.removeIf(availableAction -> availableAction.action == action);
    }

    public Player getPlayer() {
        return player;
    }

    public List<AvailableAction> getActions() {
        return actions;
    }

    @Override
    public String toString() {
        return "PlayerActions{" +
                "player=" + player +
                ", actions=" + actions +
                '}';
    }
}
