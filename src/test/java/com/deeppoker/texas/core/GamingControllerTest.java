package com.deeppoker.texas.core;

import com.deeppoker.texas.core.playing.AvailableAction;
import com.deeppoker.texas.core.playing.PlayerActions;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class GamingControllerTest {
    private Random random = new Random();


    @Test
    public void test1() throws IOException {
        FileWriter fileWriter = new FileWriter(new File("/opt/logs/play.txt"));
        BufferedWriter bf = new BufferedWriter(fileWriter);
        for (int i = 0; i < 1; i++) {
            bf.write("-------------------");
            bf.newLine();
            test(bf);
            bf.newLine();
        }
        bf.close();
    }


    public void test(BufferedWriter fileWriter) throws IOException {
        List<Player> players = Lists.newArrayList();
        for (int i = 1; i <= 5; i++) {
            Player player = new Player(i, random.nextInt(50) + 10, i);
            players.add(player);
        }

        GamingController controller = new GamingController(players, 1);

        int ante = controller.betAnte(0);

        System.out.println("Ante " + ante + " has bet.");

        controller.betBlind();
        System.out.println("Small Blind = 1 and Big Blind 2 has bet.");
        fileWriter.write("Small Blind = 1 and Big Blind 2 has bet.");
        fileWriter.newLine();

        controller.deal();
        System.out.println("DEAL:");

        fileWriter.write("Player info:");
        fileWriter.newLine();

        for (Player player1 : players) {
            String role = null;
            switch ((int) player1.getId()) {
                case 1:
                    role = "Button";
                    break;
                case 2:
                    role = "Small Blinds";
                    break;
                case 3:
                    role = "Big Blinds";
                    break;
                case 4:
                    role = "Under the GUN";
                    break;
                default:
                    role = "player";
            }
            String message = String.format("\tPlayer:%s, bankRoll:%s, hands card: %s, role %s",
                    player1.getId(), player1.getBankRoll().getValue(), controller.getHandsCard(player1), role);

            System.out.println(message);
            fileWriter.write(message);
            fileWriter.newLine();
        }


        System.out.println("Playing:");
        fileWriter.write("Playing");
        fileWriter.newLine();

        Player player;
        TexasRound round = null;
        while (!controller.gameOver()) {

            TexasRound newRound = controller.round();
            if (newRound != round) {
                System.out.println("Board " + controller.getBoard());
                round = newRound;
            }

            PlayerActions playerActions = controller.actions();
            player = playerActions.getPlayer();
            List<AvailableAction> actions = playerActions.getActions();

            System.out.println(String.format("\tPlayer %s can:", player));
            fileWriter.write(String.format("\tPlayer %s can:", player));
            fileWriter.newLine();

            for (AvailableAction availableAction : actions) {
                System.out.println("\t\t" + availableAction.action + " " + availableAction.minBet);
                fileWriter.write("\t\t" + availableAction.action + " " + availableAction.minBet);
                fileWriter.newLine();
            }

            AvailableAction action = chose(actions);

            System.out.println("\t" + round + "--> Player " + player.getId() + " chose " + action.action + " bet " + action.minBet);
            fileWriter.write("\t" + round + "--> Player " + player.getId() + " chose " + action.action + " bet " + action.minBet);
            fileWriter.newLine();

            controller.play(player, action.action, action.minBet);
        }

        GameResult result = controller.getGameResult();
        if (result.canRunItTwice()) {
            result.showdown(true);
        } else if (result.getSurviving().size() == 1) {
            result.winnerOne();
        } else {
            result.showdown(false);
        }


        List<GameResult.ResultRound> resultRound = result.getResultRounds();
        int i = 0;
        for (GameResult.ResultRound rr : resultRound) {
            List<Showdown> showdowns = rr.showdowns;
            int ssr = (++i);
            System.out.println("Showdown:" + ssr);
            fileWriter.write("Showdown round " + ssr);
            fileWriter.newLine();

            for (Showdown showdown : showdowns) {
                // int value = result.get(showdown.getPlayer());
                // String flag = value > 0 ? "赢" : value == 0 ? "平" : "输";
                String message = String.format("\tPlay : %s, hands card %s, board %s card compose %s",
                        showdown.getPlayer().getId(),
                        showdown.getHandsCard(),
                        rr.board,
                        showdown.getCardCompose()
                );
                fileWriter.write(message);
                fileWriter.newLine();

                System.out.println(message);
            }


            System.out.println("BankRoll Change:");
            fileWriter.write("BankRoll Change:");
            fileWriter.newLine();
        }

        Scoreboard scoreboard = result.getScoreboard();
        for (Map.Entry<Player, Integer> entry : scoreboard.getChips().entrySet()) {
            Player key = entry.getKey();
            Integer value = entry.getValue();
            String flag = value > 0 ? "赢" : value == 0 ? "平" : "输";
            String message = String.format("\tPlay : %s, hands card %s, result %s, %s ",
                    key.getId(),
                    controller.getHandsCard(key),
                    flag,
                    value
            );

            fileWriter.write(message);
            fileWriter.newLine();
            System.out.println(message);
        }
        System.out.println();
    }

    private AvailableAction chose(List<AvailableAction> actions) {
        List<Pair<TexasAction, Double>> list = actions.stream().map(a -> new Pair<TexasAction, Double>(a.action, weight(a.action))).collect(Collectors.toList());

        WeightRandom<TexasAction, Double> weightRandom = new WeightRandom<>(list);
        TexasAction key = weightRandom.random();
        for (AvailableAction availableAction : actions) {
            if (availableAction.action == key) {
                return availableAction;
            }
        }
        return null;
    }


    private double weight(TexasAction texasAction) {
        switch (texasAction) {
            case BET:
                return 9;
            case CALL:
                return 8;
            case CHECK:
                return 7;
            case FOLD:
                return 1;
            case RAISE:
                return 3;
            case ALLIN:
                return 2;
        }
        return 1;
    }

}
