package com.deeppoker.texas.core.playing;

import com.deeppoker.texas.core.Player;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.stream.Collectors;

public class Pots {
    private List<Map<Player, Pot>> pots = Lists.newArrayList();

    /*
        分边池的逻辑:
        1 在此Batting Round中有1-N个玩家allin且小于其他非弃牌玩家的bet；
        2 按此Batting Round中allin的bet的大小顺序，由小到大依次切分；
        3 如果有2个玩家allin的bet一样，则只分一个；
    */
    public void makeSidePot() {
        if (pots.isEmpty()) return;
        Preconditions.checkState(watermark());

        for (Player player : allinPlayers()) {
            if (current().containsKey(player) && current().get(player).pot < currentMaxBet()) { //需要分边池
                Map<Player, Pot> sidePot = Maps.newHashMap();
                current().entrySet().stream()
                        .filter(s -> !s.getKey().equals(player))
                        .filter(s -> !s.getKey().isFold())
                        .filter(s -> s.getValue().pot > current().get(player).pot)
                        .forEach(s -> {
                            //从当前Player bet的筹码x，切出（x- 当前AllinPlayer的Allin bet值）放到新的sidePot
                            Pot pot = s.getValue().cut(current().get(player).pot);
                            sidePot.put(s.getKey(), pot);
                        });
                pots.add(sidePot);
            }
        }
        //  System.out.println("Post" + pots);
    }


    public Map<Player, Pot> current() {
        if (pots.size() == 0) {
            pots.add(Maps.newHashMap());
        }
        return pots.get(pots.size() - 1);
    }

    public boolean watermark() {
        if (pots.isEmpty()) return false;
        Map<Player, Pot> last = pots.get(pots.size() - 1);
        if (last.size() == 1) {
            Player player = new ArrayList<>(last.keySet()).get(0);
            return player.isAllin();
        }
        return watermark(last);
    }


    //将彩池切分一半出去
    public Pots split() {
        Pots pots = new Pots();
        for (Map<Player, Pot> pot : this.pots) {
            Map<Player, Pot> copy = Maps.newHashMap();
            pot.forEach((k, v) -> copy.put(k, v.cut(v.pot >> 1)));
            pots.pots.add(copy);
        }
        return pots;
    }

    private int currentMaxBet() {
        OptionalInt optionalInt = current().values()
                .stream().mapToInt(p -> p.pot).max();
        return optionalInt.orElse(0);
    }


    private List<Player> allinPlayers() {
        return current().entrySet()
                .stream()
                .filter(s -> s.getKey().isAllin())
                .sorted(Comparator.comparingInt(s -> s.getValue().pot))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    private boolean watermark(Map<Player, Pot> potMap) {
        List<Pot> pots = potMap.entrySet().stream()
                .filter(p -> !p.getKey().isFold())
                .filter(p -> !p.getKey().isAllin())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        if (pots.isEmpty()) return true;

        OptionalInt optionalInt = potMap.entrySet()
                .stream()
                .filter(p -> p.getKey().isAllin())
                .mapToInt(p -> p.getValue().pot).max();

        int maxAllinBet = optionalInt.orElse(0);
        int x = pots.get(0).pot;

        for (Pot pot : pots) {
            if (pot.pot != x || pot.pot < maxAllinBet) {
                return false;
            }
        }
        return true;
    }

    public List<Map<Player, Pot>> getPots() {
        return pots;
    }

    @Override
    public String toString() {
        return "Pots{" +
                "pots=" + pots +
                '}';
    }
}
