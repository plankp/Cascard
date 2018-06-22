/**
 *   Cascard  Copyright (C) 2018  Atoiks-Games <atoiks-games@outlook.com>
 *   This program comes with ABSOLUTELY NO WARRANTY;
 *   This is free software, and you are welcome to redistribute it
 *   under certain conditions;
 */

package org.atoiks.games.cascard.environment;

import org.atoiks.games.cascard.Card;
import org.atoiks.games.cascard.Game;
import org.atoiks.games.cascard.Hand;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.function.Supplier;

public class ConsoleGame extends Game {

    private final BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public boolean init() {
        System.out.println("How many players are playing?");
        int inp = -1;
        while (inp < 2) {
            System.out.print("(enter 2 to k)");
            try {
                inp = Integer.parseInt(sysin.readLine().trim());
            } catch (IOException | NullPointerException ex) {
                return false;
            } catch (NumberFormatException ex) {
                // restart the loop, ask for input again
            }
        }

        final Hand[] players = new Hand[inp];
        for (int i = 0; i < players.length; ++i) {
            players[i] = new Hand();
        }
        this.setPlayers(players);

        return super.init();
    }

    @Override
    protected Card pickCard(final int id, final Hand player) {
        System.out.println("Top card is " + topCard);
        System.out.println("Player " + (id + 1) + ": Pick your card:");
        player.forEachCard(card -> System.out.println("- " + card));

        final int upperBound = player.getRemainingCards();
        if (upperBound == 1) {
            System.out.println("Only one card left, auto-playing it");
            return player.playFirstCard();
        }

        int inp = -1;
        while (inp < 1 || inp > upperBound) {
            System.out.print("(enter 1 to " + upperBound + "): ");
            try {
                inp = Integer.parseInt(sysin.readLine().trim());
            } catch (IOException | NullPointerException ex) {
                return null;
            } catch (NumberFormatException ex) {
                // restart the loop, ask for input again...
            }
        }

        player.compact();
        return player.playCard(inp - 1);
    }

    @Override
    protected void broadcastWinner(int id, Hand hand) {
        System.out.println("Player " + (id + 1) + " won!");
    }


    @Override
    public void close() {
        try {
            sysin.close();
        } catch (IOException ex) {
            //
        }
    }
}