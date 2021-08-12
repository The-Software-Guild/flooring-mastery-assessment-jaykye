package dao;

import dto.Order;
import dto.Product;
import dto.Tax;

import java.util.List;

public interface FlooringMasteryDao {

    /**
     * Adds an order object to the order HashMap.
     * @param order
     */
    Order addOrder(Order order);

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

    void loadAllData() throws FlooringMasteryPersistenceException;

    /**
     * Saves all the order data and separate them into different files based on the order dates.
     * @throws FlooringMasteryPersistenceException
     */
    void saveAllOrderData() throws FlooringMasteryPersistenceException;

    /**
     * This exports all the orders data to the Master file.
     * @throws FlooringMasteryPersistenceException
     */
    void exportToMasterOrderData() throws FlooringMasteryPersistenceException;

    Tax getTax(String state);

    Product getProduct(String productType);

}
