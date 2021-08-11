package dao;

import dto.Order;

import java.util.List;

public interface FlooringMasteryDao {

    /**
     * Adds an order object to the order HashMap.
     * @param orderNumber
     * @param order
     */
    Order addOrder(int orderNumber, Order order);

    /**
     * Edits an order object in the order HashMap.
     * @param orderNumber
     */
    Order editOrder(int orderNumber, int fieldNumber, String newValue);

    /**
     * Removes an order object from the order HashMap.
     * @param orderNumber
     */
    Order removeOrder(int orderNumber);

    /**
     * Retrieves an order object from the order HashMap.
     * @param orderNumber
     */
    Order getOrder(int orderNumber);

    /**
     * Retrieves all order objects from the order HashMap.
     */
    List<Order> getAllOrder();


}
