package com.deeppoker.texas.core;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Scoreboard implements Cloneable {
    private Map<Player, Integer> chips = Maps.newHashMap();

    public Scoreboard(List<Player> player) {
        player.forEach(p -> chips.put(p, p.getBankRoll().getValue()));
    }

    private Scoreboard() {
    }

    public void counter() {
        chips.keySet().forEach(p -> {
            int value = chips.get(p);
            chips.put(p, p.getBankRoll().getValue() - value);
        });
    }

    public Map<Player, Integer> winner() {
        HashMap<Player, Integer> finalOut = new LinkedHashMap<>();
        chips.entrySet()
                .stream()
                .filter(e -> e.getValue() > 0)
                .sorted((p1, p2) -> p2.getValue().compareTo(p1.getValue()))
                .collect(Collectors.toList()).forEach(ele -> finalOut.put(ele.getKey(), ele.getValue()));
        return finalOut;
    }


    public Map<Player, Integer> loser() {
        HashMap<Player, Integer> finalOut = new LinkedHashMap<>();
        chips.entrySet()
                .stream()
                .filter(e -> e.getValue() < 0)
                .sorted((p1, p2) -> p1.getValue().compareTo(p2.getValue()))
                .collect(Collectors.toList()).forEach(ele -> finalOut.put(ele.getKey(), ele.getValue()));
        return finalOut;
    }

    public Map<Player, Integer> result() {
        HashMap<Player, Integer> finalOut = new LinkedHashMap<>();
        chips.entrySet()
                .stream()
                .sorted((p1, p2) -> p2.getValue().compareTo(p1.getValue()))
                .collect(Collectors.toList()).forEach(ele -> finalOut.put(ele.getKey(), ele.getValue()));
        return finalOut;
    }

    @Override
    protected Scoreboard clone() {
        Scoreboard scoreboard = new Scoreboard();
        this.chips.forEach((k, v) -> scoreboard.chips.put(k, v));
        return scoreboard;
    }

    public Map<Player, Integer> getChips() {
        return chips;
    }
}
