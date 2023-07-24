import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Test
    void nullHorseNameTest() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1.0));
    }

    @Test
    void nullHorseNameExcMessageTest() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1.0));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n", "\s\s\s", "\r", "\f"})
    void emptyHorseNameTest(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1.0));
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n", "\s\s\s", "\r", "\f"})
    void emptyHorseNameExcMessageTest(String name) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1.0));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void negativeSpeedTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse("SomeName", -1.0));
    }

    @Test
    void negativeSpeedExcMessageTest() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("SomeName", -2.0));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void negativeDistanceTest() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("SomeName", 1.0, -1.0));
    }

    @Test
    void negativeDistanceExcMessageTest() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("SomeName", 1.0, -1.0));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getNameTest() {
        String name = "SomeName";
        assertEquals(name, new Horse(name, 1.0).getName());
    }

    @Test
    void getSpeedTest() {
        double speed = 10.0;
        assertEquals(speed, new Horse("SomeName", speed).getSpeed());
    }

    @Test
    void getDistanceTest() {
        double distance = 10.0;
        Horse horseZeroDistance = new Horse("SomeName", 1.0);

        assertEquals(0, horseZeroDistance.getDistance());
        assertEquals(distance, new Horse("SomeName", 1.0, distance).getDistance());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.3, 0.4, 0.5})
    void moveTest(double randomDouble) {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("SomeName", 1.0, 1.0);
            double expectedDistance = horse.getDistance() + horse.getSpeed() * randomDouble;
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomDouble);

            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
            assertEquals(expectedDistance, horse.getDistance());
        }
    }

}