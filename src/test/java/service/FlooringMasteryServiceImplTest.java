package service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class FlooringMasteryServiceImplTest {

    FlooringMasteryServiceImpl service = new FlooringMasteryServiceImpl();

    @Test
    public void ValidateFileDateStringTest() {
        String correctString = "11122021";
        String wrongString1 = "13122021";
        String wrongString2 = "13135021";
        String wrongString3 = "131220213";
        String wrongString4 = "1312202";



        assertDoesNotThrow(() -> service.validateFileDateString(correctString));

        assertThrows(
                FlooringMasteryInvalidDateInputException.class,
                () -> service.validateFileDateString(wrongString1));

        assertThrows(
                FlooringMasteryInvalidDateInputException.class,
                () -> service.validateFileDateString(wrongString2));

        assertThrows(
                FlooringMasteryInvalidDateInputException.class,
                () -> service.validateFileDateString(wrongString3));

        assertThrows(
                FlooringMasteryInvalidDateInputException.class,
                () -> service.validateFileDateString(wrongString4));

    }

}