package service;

import dto.Order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FlooringMasteryServiceImpl implements FlooringMasteryService {
    public FlooringMasteryServiceImpl() {
    }

    @Override
    public void addOrder() {

    }

    @Override
    public void editOrder() {

    }

    @Override
    public void removeOrder() {

    }

    @Override
    public void exportAllData() {

    }

    @Override
    public Order updateCalculatedFields(Order order) {
        return null;
    }

    public void validateFileDateString(String dateString)
            throws FlooringMasteryInvalidDateInputException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyy");
        try {
            LocalDate.parse(dateString, formatter);
        }
        catch (DateTimeParseException e) {
            throw new FlooringMasteryInvalidDateInputException(
                    "Datetime format is wrong. Please use MMddyyyy format", e);
        }
    }
}
