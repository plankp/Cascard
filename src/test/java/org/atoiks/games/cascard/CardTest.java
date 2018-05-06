package org.atoiks.games.cascard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardTest {

    @Test
    public void cardsCompareById() {
        final Card a = new Card(1);
        final Card b = new Card(2);

        assertTrue(a.compareTo(b) < 0);
        assertTrue(b.compareTo(a) > 0);

        assertTrue(a.compareTo(a) == 0);
        assertTrue(b.compareTo(b) == 0);
    }
}