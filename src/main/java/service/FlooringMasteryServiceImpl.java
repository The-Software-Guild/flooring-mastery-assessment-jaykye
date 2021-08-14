package service;

import dao.FlooringMasteryDao;
import dao.FlooringMasteryDaoFileImpl;
import dao.FlooringMasteryPersistenceException;
import dto.Order;
import dto.Product;
import dto.Tax;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class FlooringMasteryServiceImpl implements FlooringMasteryService {
    FlooringMasteryDao dao;

    public FlooringMasteryServiceImpl() {
         dao = new FlooringMasteryDaoFileImpl();
    }

    public FlooringMasteryServiceImpl(FlooringMasteryDao dao) {
        this.dao = dao;
    }

    @Override
    public Order addOrder(Order order) throws
            FlooringMasteryInvalidOrderException,
            FlooringMasteryInvalidDateInputException{
        // Area is checked with the actual value before the order object is passed.

        validateOrder(order);
        updateCalculatedFields(order);
        validateFileDateString(order.getOrderDate());

        return dao.addOrder(order);
    }

    @Override
    public List<Order> getOrdersForADate(String date) {
        List<Order> allOrderList = getAllOrders();
        return allOrderList.stream().filter(order -> order.getOrderDate().equals(date)).collect(Collectors.toList());
    }

    @Override
    public Order getOrder(int orderNumber) {
        return dao.getOrder(orderNumber);
    }

    @Override
    public List<Order> getAllOrders() {
        return dao.getAllOrder();
    }

    @Override
    public Order editOrder(int orderNumber, int fieldNumber, String newValue) throws
            FlooringMasteryInvalidOrderException{
        try {
            Order order = dao.editOrder(orderNumber, fieldNumber, newValue);
            // Validate the order info.
            validateOrder(order);
            updateCalculatedFields(order);
        }
        catch (NumberFormatException e) {
            throw new FlooringMasteryInvalidOrderException("The number entered is not valid");
        }

        return null;
    }

    @Override
    public Order removeOrder(int orderNumber) {
        return dao.removeOrder(orderNumber);
    }

    @Override
    public void exportAllData() throws FlooringMasteryPersistenceException {
        dao.exportToMasterOrderData();
    }

    @Override
    public void loadAllData() throws FlooringMasteryPersistenceException {
        dao.loadAllData();
    }

    @Override
    public void saveAllOrderData() throws FlooringMasteryPersistenceException {
        dao.saveAllOrderData();
    }

    /**
     * Calculates the calculated fields. The list of fields updated are:
     * calculated - Material cost, Labor cost, tax, total
     * dependent - tax rate, cost per square foot, labor cost per square foot.
     * @param order - The order to update calculation. Usually after edit method.
     * @return Order
     */

    private Order updateCalculatedFields(Order order) {
        // Objects are mutable, so I can directly make changes.
        // Dependent values
        // get appropriate tax and product objects to refer.

        String state = order.getState();
        String productType = order.getProductType();

        Tax tax = dao.getTax(state);
        Product product = dao.getProduct(productType);

        order.setTaxRate(tax.getTaxRate());
        order.setCostPerSquareFoot(product.getCostPerSquareFoot());
        order.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());

        // Calculated values
        BigDecimal materialCost = order.getArea().multiply(
                order.getCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal laborCost = order.getArea().multiply(
                order.getLaborCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP);
        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);

        BigDecimal totalTax = order.getMaterialCost().add(
                order.getLaborCost()).multiply(
                        order.getTaxRate().divide(
                                new BigDecimal("100"))).setScale(2, RoundingMode.HALF_UP);
        order.setTax(totalTax);

        BigDecimal total = order.getMaterialCost().add(order.getLaborCost()).add(order.getTax());
        order.setTotal(total);

        return order;
    }

    public void validateFileDateString(String dateString)
            throws FlooringMasteryInvalidDateInputException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyy");
        LocalDate orderDate;
        try {
            orderDate = LocalDate.parse(dateString, formatter);
        }
        catch (DateTimeParseException e) {
            throw new FlooringMasteryInvalidDateInputException(
                    "Datetime format is wrong. Please use MMddyyyy format", e);
        }

        if (orderDate.compareTo(LocalDate.now()) < 1) {
            throw new FlooringMasteryInvalidDateInputException(
                    "The order date must be in the future.");
        }
    }

    /**
     * This validates user input values. Area value is checked when set.
     * @param order The order object to validate.
     * @throws FlooringMasteryInvalidOrderException
     */
    public void validateOrder(Order order) throws FlooringMasteryInvalidOrderException {
        if (order.getCustomerName() == null
        || order.getCustomerName().trim().length() == 0) {
            throw new FlooringMasteryInvalidOrderException("#### The Customer name in the order is not valid.");
        }
        else if (dao.getTax(order.getState()) == null) {
            throw new FlooringMasteryInvalidOrderException("#### The State in the order is not valid.");
        }
        else if (dao.getProduct(order.getProductType()) == null) {
            throw new FlooringMasteryInvalidOrderException("#### The Product type in the order is not valid.");
        }
        else if (order.getArea().compareTo(new BigDecimal("0")) == 0) {
            throw new FlooringMasteryInvalidOrderException("#### The Area value cannot be zero.");
        }
        else if (order.getOrderDate() == null || order.getOrderDate() == "") {
            throw new FlooringMasteryInvalidOrderException("#### Enter order date.");
        }
    }

    public int getNextOrderNumber() {
        int maxNum = 1;
        int currOrderNumber = 1;

        List<Order> allOrderList = dao.getAllOrder();
        for (Order order : allOrderList) {
            currOrderNumber = order.getOrderNumber();
            if (currOrderNumber > maxNum) {
                maxNum = currOrderNumber;
            }
        }
        return maxNum + 1;
    }
}
