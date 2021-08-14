package service;

import dao.FlooringMasteryPersistenceException;
import dto.Order;

import java.util.List;

public interface FlooringMasteryService {

    /**
     * Adds an order. The method will query the user for necessary data.
     */
    public Order addOrder(Order order) throws FlooringMasteryInvalidOrderException, FlooringMasteryInvalidDateInputException;
    public List getOrdersForADate(String date);
    Order getOrder(int orderNumber);
    List<Order> getAllOrders();
    public Order editOrder(int orderNumber, int fieldNumber, String newValue) throws FlooringMasteryInvalidOrderException;
    public Order removeOrder(int orderNumber);
    public void exportAllData() throws FlooringMasteryPersistenceException;

    void loadAllData() throws FlooringMasteryPersistenceException;
    void saveAllOrderData() throws FlooringMasteryPersistenceException;
    public int getNextOrderNumber();
}
