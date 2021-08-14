package controller;

import dao.FlooringMasteryDao;
import dao.FlooringMasteryDaoFileImpl;
import dao.FlooringMasteryPersistenceException;
import dto.Order;
import service.FlooringMasteryInvalidDateInputException;
import service.FlooringMasteryInvalidOrderException;
import service.FlooringMasteryService;
import service.FlooringMasteryServiceImpl;
import ui.FlooringMasteryView;

import java.text.NumberFormat;
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
        // Start up
        view.displayGreetingMessage();
        try {
            service.loadAllData();
        }
        catch (FlooringMasteryPersistenceException e) {
            System.out.println("Application failed to load data. Closing.");
        }


        boolean notDone = true;
        while (notDone) {
            int mainMenuSelection = view.displayMainMenuAndGetSelection();
            int orderNumber;
            int nextOrderNumber;
            // switch to user selection

            switch (mainMenuSelection) {
                case 1: // Display order
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
                    break;

                case 2: // Add an order
                    view.addOrderMessage();
                    nextOrderNumber = service.getNextOrderNumber();

                    boolean hasError = false;
                    do {
                        try {
                            Order newOrder = view.getOrderInfo(new Order(nextOrderNumber));
                            service.addOrder(newOrder); // This validates input data and populates dependent fields.
                            hasError = false;
                        }
                        catch (FlooringMasteryInvalidOrderException e) {
                            hasError = true;
                            view.displayErrorMessage(e);
                        }
                        catch (NumberFormatException e) {
                            hasError = true;
                            view.displayWrongNumberFormatMessage();
                        }
                        catch (FlooringMasteryInvalidDateInputException e) {
                            hasError = true;
                            view.displayErrorMessage(e);
                        }
                    }
                    while (hasError);

                    view.addedOrderMessage();
                    view.waitForUserInput();

                    break;

                case 3: // Edit an order
                    view.editOrderMessage();

                    hasError = false;
                    do {
                        orderNumber = view.getOrderNumber();
                        if (service.getOrder(orderNumber) == null) {
                            view.displayOrderNotFound();
                            hasError = true;
                        } else {
                            hasError = false;
                        }
                        ;
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

                    break;

                case 4: // Remove an order.
                    view.removeOrderMessage();
                    hasError = false;
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

                    break;

                case 5: // Export All data.
                    view.exportDataMessage();
                    try {
                        service.exportAllData();
                    } catch (FlooringMasteryPersistenceException e) {
                        view.exportDataFailedMessage();
                        view.displayErrorMessage(e);
                    }
                    view.waitForUserInput();

                    break;

                case 6: // Quit
                    view.goodbyeMessage();
                    try {
                        service.saveAllOrderData();

                    }
                    catch (FlooringMasteryPersistenceException e) {
                        view.displayErrorMessage(e);
                    }
                    notDone = false;

                    break;

            }
        }

    }
}
