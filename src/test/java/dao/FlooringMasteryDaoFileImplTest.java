package dao;
import dto.Order;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlooringMasteryDaoFileImplTest {
    FlooringMasteryDaoFileImpl testDao;

    @BeforeEach
    public void setUp() throws Exception{
        // Use the FileWriter to quickly blank the file
        String testFilePath = "./testdata/Orders/Orders_08102021.txt";
        FileWriter writer = new FileWriter(testFilePath);
        String testDataDir = "./testdata/";
        testDao = new FlooringMasteryDaoFileImpl(testDataDir);
        writer.close();
    }

    @Test
    void addAndGetOrderTest() throws Exception{
        // Arrange
        String filePath = testDao.findOrderFile("08102021");
        testDao.loadData(filePath);
        int orderNumber = 1;
        Order newOrder = new Order(orderNumber);
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

        // Act
        testDao.addOrder(orderNumber, newOrder);
        Order retrievedOrder = testDao.getOrder(orderNumber);

        // Assert
        assertEquals(retrievedOrder, newOrder);
    }

    @Test
    void editOrder() throws Exception{
        // Arrange
        String filePath = testDao.findOrderFile("08102021");
        testDao.loadData(filePath);
        int orderNumber = 1;
        Order newOrder = new Order(orderNumber);
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
        testDao.addOrder(orderNumber, newOrder);

        // Act
        testDao.editOrder(orderNumber, 1, "newName");
        Order retrievedOrder = testDao.getOrder(orderNumber);

        // Assert
        assertEquals(retrievedOrder.getCustomerName(), "newName");

    }

    @Test
    void removeOrder() throws Exception{
        // Arrange
        String filePath = testDao.findOrderFile("08102021");
        testDao.loadData(filePath);
        int orderNumber = 1;
        Order newOrder = new Order(orderNumber);
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
        testDao.addOrder(orderNumber, newOrder);

        // Act
        testDao.removeOrder(orderNumber);
        Order retrievedOrder = testDao.getOrder(orderNumber);

        // Assert
        assertNull(retrievedOrder);

    }

    @Test
    void getAllOrder() throws Exception{
        // Arrange
        String filePath = testDao.findOrderFile("08102021");
        testDao.loadData(filePath);
        int orderNumber = 1;
        Order newOrder = new Order(orderNumber);
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
        testDao.addOrder(orderNumber, newOrder);

        // Act
        List allOrders = testDao.getAllOrder();

        // Assert
        assertEquals(allOrders.size(), 1);
        assertTrue(allOrders.contains(newOrder));

    }

    @Test
    void findOrderFile() throws Exception{
        // Arrange is already done.

        // Act
        String newFilePath = "./testdata/Orders/Orders_08102021.txt";
        File newFile = new File(newFilePath);
        String file = testDao.findOrderFile("08102021");

        // Assert
        assertEquals(
                (newFilePath).replace("/",""),
                file.replace("\\", ""));

        // Delete file
        newFile.delete();
    }
}