package service;

import dao.FlooringMasteryPersistenceException;
import dto.Order;

import java.util.List;

public interface FlooringMasteryService {

    /**
     * Adds an order. The method will query the user for necessary data.
     */
    Order addOrder(Order order) throws FlooringMasteryInvalidOrderException, FlooringMasteryInvalidDateInputException;

    List getOrdersForADate(String date);

    Order getOrder(int orderNumber);

    List<Order> getAllOrders();

    Order editOrder(int orderNumber, int fieldNumber, String newValue) throws FlooringMasteryInvalidOrderException;

    Order removeOrder(int orderNumber);

    void exportAllData() throws FlooringMasteryPersistenceException;

    void loadAllData() throws FlooringMasteryPersistenceException;

    void saveAllOrderData() throws FlooringMasteryPersistenceException;

    int getNextOrderNumber();
}
