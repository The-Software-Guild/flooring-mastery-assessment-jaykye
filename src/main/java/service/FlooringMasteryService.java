package service;

import dto.Order;

public interface FlooringMasteryService {

    /**
     * Adds an order. The method will query the user for necessary data.
     */
    public void addOrder();
    public void editOrder();
    public void removeOrder();
    public void exportAllData();

    /**
     * Updates all the calculated fields in an Order object.
     * @param order: The Order object to update the calculation.
     */
    Order updateCalculatedFields(Order order);
}
