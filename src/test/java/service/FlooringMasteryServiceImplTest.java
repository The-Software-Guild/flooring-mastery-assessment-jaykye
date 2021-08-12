package service;

import static org.junit.jupiter.api.Assertions.*;

import dao.FlooringMasteryDao;
import dao.FlooringMasteryDaoFileImpl;
import dto.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class FlooringMasteryServiceImplTest {
    FlooringMasteryDao testDao = new FlooringMasteryDaoFileImpl("testdata/");
    FlooringMasteryServiceImpl testService = new FlooringMasteryServiceImpl(testDao);


    @Test
    public void ValidateFileDateStringTest() {
        String correctString = "11122021";
        String wrongString1 = "13122021";
        String wrongString2 = "13135021";
        String wrongString3 = "131220213";
        String wrongString4 = "1312202";



        assertDoesNotThrow(() -> testService.validateFileDateString(correctString));

        assertThrows(
                FlooringMasteryInvalidDateInputException.class,
                () -> testService.validateFileDateString(wrongString1));

        assertThrows(
                FlooringMasteryInvalidDateInputException.class,
                () -> testService.validateFileDateString(wrongString2));

        assertThrows(
                FlooringMasteryInvalidDateInputException.class,
                () -> testService.validateFileDateString(wrongString3));

        assertThrows(
                FlooringMasteryInvalidDateInputException.class,
                () -> testService.validateFileDateString(wrongString4));

    }

    @Test
    public void addAndGetOrderTest(){
        // Arrange
        int orderNumber1 = 1;
        Order newOrder = new Order(orderNumber1);
        newOrder.setCustomerName("Customer");
        newOrder.setState("TX");
        newOrder.setTaxRate(new BigDecimal("1.23"));
        newOrder.setProductType("asdf");
        newOrder.setArea(new BigDecimal("1.23"));
        newOrder.setCostPerSquareFoot(new BigDecimal("123"));
        newOrder.setLaborCostPerSquareFoot(new BigDecimal("123"));
        newOrder.setMaterialCost(new BigDecimal("123"));
        newOrder.setLaborCost(new BigDecimal("123"));
        newOrder.setTax(new BigDecimal("123"));
        newOrder.setTotal(new BigDecimal("1.23"));


        testService.addOrder(newOrder);

        assertEquals(newOrder, testService.getOrder(orderNumber1));
    }

    @Test
    public void editOrderTest() throws Exception{
        testService.loadAllData();
        // Arrange
        int orderNumber1 = 1;
        Order newOrder = new Order(orderNumber1);
        newOrder.setCustomerName("Customer");
        newOrder.setState("TX");
        newOrder.setProductType("Wood");
        newOrder.setArea(new BigDecimal("1.00"));
        testService.addOrder(newOrder);

        int nameField = 1;
        int areaField = 4;
        String newName = "new Name";
        String newArea = "2.00";

        testService.editOrder(orderNumber1, nameField, newName);

        testService.editOrder(orderNumber1, areaField, newArea);
        assertEquals(newName, testService.getOrder(orderNumber1).getCustomerName());
        assertEquals(new BigDecimal("20.68"), testService.getOrder(orderNumber1).getTotal());
    }

    @Test
    public void removeAndGetALLOrderTest() throws Exception{
        // Arrange
        int orderNumber1 = 1;
        Order newOrder = new Order(orderNumber1);
        newOrder.setCustomerName("Customer");
        newOrder.setState("TX");
        newOrder.setProductType("Wood");
        newOrder.setArea(new BigDecimal("1.00"));
        testService.addOrder(newOrder);

        // Act
        testService.removeOrder(orderNumber1);

        // Assert
        assertEquals(0, testService.getAllOrders().size());
    }
}