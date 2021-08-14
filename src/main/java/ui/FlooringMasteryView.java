package ui;

import dto.Order;

import java.math.BigDecimal;
import java.util.List;

public class FlooringMasteryView {
    UserIO io;

    public FlooringMasteryView() {
        UserIO io = new UserIOConsoleImpl();
    }

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public void displayGreetingMessage() {
        io.print("Hello user!");
    }

    public int displayMainMenuAndGetSelection() {
        int menuNum = 1;
        io.print("  * * * * * * * * * * * Main Menu * * * * * * * * * * * * * * * *");
        io.print(menuNum++ + ". Display Orders");
        io.print(menuNum++ + ". Add an Order");
        io.print(menuNum++ + ". Edit an Order");
        io.print(menuNum++ + ". Remove an Order");
        io.print(menuNum++ + ". Export All Data");
        io.print(menuNum++ + ". Quit");

        io.print("  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

        return io.readInt("Select a number from the above menu", 1, menuNum);
    }

    public void displayOrdersMessage() {
        io.print("Display order selected.");
    }

    public String getOrderDate(){
        return io.readString("Enter the order date in mmddyyy format.");
    }

    public void displayAnOrder(Order order) {
        int orderNumber = order.getOrderNumber();
        String customerName = order.getCustomerName();
        String state = order.getState();
        BigDecimal taxRate = order.getTaxRate();
        String  productType = order.getProductType();
        BigDecimal area = order.getArea();
        BigDecimal costPerSquareFoot = order.getCostPerSquareFoot();
        BigDecimal laborCostPerSquareFoot = order.getLaborCostPerSquareFoot();
        BigDecimal materialCost = order.getMaterialCost();
        BigDecimal laborCost = order.getLaborCost();
        BigDecimal tax = order.getTax();
        BigDecimal total = order.getTotal();
        String date = order.getOrderDate();
        io.print("Order number: " + orderNumber
                + " Customer Name: " + customerName
                + " State: " + state
                + " Tax Rate: " + taxRate
                + " Product Type: " + productType
                + " Area: " + area
                + " Cost per Square Foot: " + costPerSquareFoot
                + " Labor cost per Square Foot: " + laborCostPerSquareFoot
                + " Material cost: " + materialCost
                + " Labor cost: " + laborCost
                + " Tax: " + tax
                + " Total: " + total
                + " Order Date: "+ date
                );
    }

    public void displayOrders(List<Order> orderList) {
        io.print("########### Listing orders: ###########"+
                "########################################" +
                "########################################" +
                "#####################################");
        for (Order order : orderList) {
            displayAnOrder(order);
        }
        io.print("#######################################" +
                "########################################" +
                "########################################" +
                "#####################################");
    }

    public void addOrderMessage() {
        io.print("Add order selected.");
    }

    public void displayErrorMessage(Exception e){
        io.print("%%%%%%%%%%%%%%%%%%%%%%%%%%");
        io.print(e.getMessage());
        io.print("%%%%%%%%%%%%%%%%%%%%%%%%%%");

    }
    public void displayWrongNumberFormatMessage() {
        io.print("The number format is not correct. Try again");
    }

    public Order getOrderInfo(Order order) throws NumberFormatException{
        String customerName = io.readString("Enter Customer name");
        String state = io.readString("Enter state abbreviation");
        String productType = io.readString("Enter Product type");
        String areaString = io.readString("Enter Area");
        String orderDate = io.readString("Enter Order Date in mmddyyyy");

        order.setCustomerName(customerName);
        order.setState(state);
        order.setProductType(productType);
        order.setArea(new BigDecimal(areaString));
        order.setOrderDate(orderDate);

        return order;
    }

    public void addedOrderMessage(){
        io.print("Order added successfully.");
    }

    public void editOrderMessage(){
        io.print("Edit order selected.");
    }

    public int getOrderNumber(){
        return io.readInt("Enter the order number of the order.");
    }

    public void displayOrderNotFound(){
        io.print("The order with the order number entered was not found.");
    }

    public String getNewCustomerName(Order order, int fieldNumber){
        switch (fieldNumber) {
            case 1:
                return io.readString("Enter customer name (" + order.getCustomerName() + ")");
            case 2:
                return io.readString("Enter state (" + order.getState() + ")");
            case 3:
                return io.readString("Enter product type (" + order.getProductType() + ")");
            case 4:
                return io.readString("Enter area (" + order.getArea() + ")");
            default:
                return "";
        }
    }

    public void editedOrderMessage(int orderNumber){
        io.print("Order number " + orderNumber + " is edited.");
    }

    public void removeOrderMessage() {
        io.print("Remove an order selected.");
    }
        public void removedOrderMessage(int orderNumber) {
        io.print("Remove an order with order number: " + orderNumber);

    }

    public void exportDataMessage(){
        io.print("Export data selected.");
    }

    public void exportDataFailedMessage(){
        io.print("Export data failed.");
    }

    public void exportDataSuccessMessage(){
        io.print("Export data successful.");
    }

    public void goodbyeMessage() {
        io.print("Thank you and goodbye.");

    }
    public void waitForUserInput() {
        io.readString("Press enter to continue.");
    }
}
