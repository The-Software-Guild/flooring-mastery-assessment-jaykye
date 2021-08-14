package controller;

import dao.FlooringMasteryPersistenceException;
import dto.Order;
import service.FlooringMasteryInvalidDateInputException;
import service.FlooringMasteryInvalidOrderException;
import service.FlooringMasteryService;
import service.FlooringMasteryServiceImpl;
import ui.FlooringMasteryView;
import java.util.List;

public class FlooringMasteryController {
    FlooringMasteryView view;
    FlooringMasteryService service;

    public FlooringMasteryController() {
        view = new FlooringMasteryView();
        service = new FlooringMasteryServiceImpl();
    }

    public FlooringMasteryController(FlooringMasteryService service, FlooringMasteryView view) {
        this.view = view;
        this.service = service;
    }

    public void run() {

        // Start up - Load data from data storage.

        view.displayGreetingMessage();
        loadData();

        boolean notDone = true;

        // Stay in the loop to return to main menu.
        while (notDone) {

            int mainMenuSelection = view.displayMainMenuAndGetSelection();

            // switch to user selection

            switch (mainMenuSelection) {
                case 1: // Display order from memory.
                    displayOrders();
                    break;

                case 2: // Add an order to the memory.
                    addAnOrder();
                    break;

                case 3: // Edit an order from memory.
                    editAnOrder();
                    break;

                case 4: // Remove an order from memory.
                    removeAnOrder();
                    break;

                case 5: // Export All data to a backup master file.
                    exportAllData();
                    break;

                case 6: // Save all the data in memory to data storage.
                    quitAndShutDown();
                    notDone = false;
                    break;

            }
        }

    }

    private void loadData() {
        try {
            service.loadAllData();
        }
        catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e);
            System.out.println("Application failed to load data. Closing.");
        }
    }

    private void quitAndShutDown() {
        view.goodbyeMessage();
        try {
            service.saveAllOrderData();

        }
        catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e);
        }
    }

    private void exportAllData() {
        view.exportDataMessage();
        try {
            service.exportAllData();
        } catch (FlooringMasteryPersistenceException e) {
            view.exportDataFailedMessage();
            view.displayErrorMessage(e);
        }
        view.waitForUserInput();
    }

    private void removeAnOrder() {

        int orderNumber;
        view.removeOrderMessage();
        boolean hasError = false;

        do {
            orderNumber = view.getOrderNumber();
            if (service.getOrder(orderNumber) == null) {
                view.displayOrderNotFound();
                hasError = true;
            }
            else {
                hasError = false;
            }
        } while (hasError);
        service.removeOrder(orderNumber);

        view.removedOrderMessage(orderNumber);
        view.waitForUserInput();
    }

    private void editAnOrder() {

        int orderNumber;
        view.editOrderMessage();

        boolean hasError = false;
        do {
            orderNumber = view.getOrderNumber();
            if (service.getOrder(orderNumber) == null) {
                view.displayOrderNotFound();
                hasError = true;
            } else {
                hasError = false;
            }
        } while (hasError);

        Order toEditOrder = service.getOrder(orderNumber);
        // iterate through independent fields.
        String newValue = "";
        for (int i = 1; i <= 4; i++) {
            do {
                try {
                    newValue = view.getNewCustomerName(toEditOrder, i);
                    if (! newValue.equals("")) {
                        service.editOrder(orderNumber, i, newValue);
                    }
                    hasError = false;
                } catch (FlooringMasteryInvalidOrderException e) {
                    hasError = true;
                }
            } while (hasError);
        }

        view.editedOrderMessage(orderNumber);
        view.waitForUserInput();
    }

    private void addAnOrder() {

        int nextOrderNumber;
        view.addOrderMessage();
        nextOrderNumber = service.getNextOrderNumber();

        boolean hasError = false;
        do {
            try {
                Order newOrder = view.getOrderInfo(new Order(nextOrderNumber));
                service.addOrder(newOrder); // This validates input data and populates dependent fields.
                hasError = false;
            }
            catch (FlooringMasteryInvalidOrderException
                    | FlooringMasteryInvalidDateInputException e) {
                hasError = true;
                view.displayErrorMessage(e);
            }
            catch (NumberFormatException e) {
                hasError = true;
                view.displayWrongNumberFormatMessage();
            }
        }
        while (hasError);

        view.addedOrderMessage();
        view.waitForUserInput();
    }

    private void displayOrders() {

        view.displayOrdersMessage();

        String orderDate = view.getOrderDate();
        List<Order> ordersOfDate = service.getOrdersForADate(orderDate);

        if (ordersOfDate.size() > 0) {
            view.displayOrders(ordersOfDate);
        }
        else {
            System.out.println("No order of selected date found.");
        }

        view.waitForUserInput();
    }
}
