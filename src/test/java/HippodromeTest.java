import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class HippodromeTest {

    @Test
    void nullHorses() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void nullHorsesExcMessage() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void emptyHorses() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<Horse>()));
    }

    @Test
    void emptyHorsesExcMessage() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<Horse>()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorsesTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++)
            horses.add(new Horse("SomeName" + (i + 1), 1.0, 1.0));

        assertEquals(horses, new Hippodrome(horses).getHorses());
    }

    @Test
    void moveTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++)
            horses.add(Mockito.mock(Horse.class));

        new Hippodrome(horses).move();

        for (Horse horse : horses)
            Mockito.verify(horse).move();
    }

    @Test
    void getWinnerTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            horses.add(new Horse("SomeName" + (i + 1), 1.0, i + 1));

        assertSame(horses.get(9), new Hippodrome(horses).getWinner());
    }

}
