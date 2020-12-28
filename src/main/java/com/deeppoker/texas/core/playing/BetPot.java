package com.deeppoker.texas.core.playing;

import com.deeppoker.texas.core.Player;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class BetPot {
    private Pots pots = new Pots();

    //下注
    public int bet(Player player, int bet, boolean allin) {
        if (!allin) {
            Preconditions.checkArgument(bet >= callBet(player));
        }
        Pot pot = new Pot(bet);
        pots.current().merge(player, pot, Pot::betMerge);
        return bet;
    }

    //单个玩家下的最大的注
    public int maxBet() {
        OptionalInt optionalInt = pots.current().values()
                .stream().mapToInt(p -> p.pot).max();
        return optionalInt.orElse(0);
    }

    //跟注应该要补充的筹码
    public int callBet(Player player) {
        int bet = maxBet();
        Pot pot = pots.current().get(player);
        if (pot == null) {
            return bet;
        }
        return bet - pot.pot;
    }

    //总彩池大小
    public int sumBet() {
        return pots.getPots()
                .stream()
                .mapToInt(e -> e.values().stream().mapToInt(p -> p.pot).sum())
                .sum();
    }


    //彩池是否水平
    public boolean watermark() {
        return pots.watermark();
    }


    public void take(Player player) {
        List<List<Player>> winner = Lists.newArrayList();
        winner.add(Lists.newArrayList(player));
        take(winner);
    }

    public void take(Player player1, Player player2) {
        List<List<Player>> winner = Lists.newArrayList();
        winner.add(Lists.newArrayList(player1, player2));
        take(winner);
    }

    public void take(List<List<Player>> winner) {
        this.getPots().makeSidePot();
        winner.forEach(players -> {
            for (Map<Player, Pot> pot : pots.getPots()) {
                List<Player> takePlayers = players.
                        stream()
                        .filter(p -> inPot(p, pot))
                        .collect(Collectors.toList());

                if (takePlayers.size() > 0) {
                    takeBetOfPot(takePlayers, pot);
                }
            }
        });
    }

    private boolean inPot(Player player, Map<Player, Pot> pot) {
        return pot.containsKey(player);
    }


    private int takeBetOfPot(List<Player> players, Map<Player, Pot> pots) {
        Preconditions.checkState(players.size() > 0);
        for (Player player : players) {
            Pot pot = pots.get(player);
            player.getBankRoll().win(pot.pot);
            pots.remove(player);
        }

        int sum = betOfPot(pots);
        if (sum > 0) {
            int betEachPlayer = sum / players.size();
            for (Player player : players) {
                player.getBankRoll().win(betEachPlayer);
            }
            int remainder = sum % players.size();
            if (remainder > 0) {
                for (int i = 0; i <= remainder; i++) {
                    players.get(i).getBankRoll().win(1);
                }
            }
        }
        pots.clear();
        return sum;
    }

    private int betOfPot(Map<Player, Pot> pots) {
        return pots.values().stream().mapToInt(v -> v.pot).sum();
    }

    public Pots getPots() {
        return pots;
    }


    public List<BetPot> split() {
        BetPot betPot = new BetPot();
        betPot.pots = this.pots.split();
        return Lists.newArrayList(betPot, this);
    }

    @Override
    public String toString() {
        return pots.toString();
    }


    public List<Integer> potSummary() {
        return pots.getPots()
                .stream()
                .map(m -> m.values().stream().mapToInt(p -> p.pot).sum())
                .collect(Collectors.toList());
    }

}