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

public class FlooringMasteryServiceImpl implements FlooringMasteryService {
    FlooringMasteryDao dao;

    public FlooringMasteryServiceImpl() {
         dao = new FlooringMasteryDaoFileImpl();
    }

    public FlooringMasteryServiceImpl(FlooringMasteryDao dao) {
        this.dao = dao;
    }

    @Override
    public Order addOrder(Order order) {
        // view will take care of getting all the data.
        return dao.addOrder(order);
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
    public Order editOrder(int orderNumber, int fieldNumber, String newValue) {
        Order order = dao.getOrder(orderNumber);

        switch (fieldNumber) {
            case 1:
                order.setCustomerName(newValue);
                break;
            case 2:
                order.setState(newValue);
                break;
            case 3:
                order.setProductType(newValue);
                break;
            case 4:
                order.setArea(new BigDecimal(newValue));
                break;
        }

        updateCalculatedFields(order);

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
        try {
            LocalDate.parse(dateString, formatter);
        }
        catch (DateTimeParseException e) {
            throw new FlooringMasteryInvalidDateInputException(
                    "Datetime format is wrong. Please use MMddyyyy format", e);
        }
    }
}
