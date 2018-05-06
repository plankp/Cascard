package org.atoiks.games.cascard;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.function.Supplier;

public class ConsoleGame extends Game {

    private BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));

    public ConsoleGame(final Supplier<Card> deck, final Hand... players) {
        this.setPlayers(players);
        this.deck = deck;
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