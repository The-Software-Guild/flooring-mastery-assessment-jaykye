package dao;
import dto.Order;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void cleanOrderDirectory(){
        File orderDir = new File("testdata/Orders/");
        File[] filelist = orderDir.listFiles();
        for (File file : filelist) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }
    }

    @Test
    void addAndGetOrderTest() throws Exception{
        // Arrange
        String filePath = testDao.findOrderFileFromDir("08102021");
        testDao.loadAnOrderData(filePath);
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
        testDao.addOrder(newOrder);
        Order retrievedOrder = testDao.getOrder(orderNumber);

        // Assert
        assertEquals(retrievedOrder, newOrder);
    }

    @Test
    void editOrder() throws Exception{
        // Arrange
        String filePath = testDao.findOrderFileFromDir("08102021");
        testDao.loadAnOrderData(filePath);
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
        testDao.addOrder(newOrder);

        // Act
        testDao.editOrder(orderNumber, 1, "newName");
        Order retrievedOrder = testDao.getOrder(orderNumber);

        // Assert
        assertEquals(retrievedOrder.getCustomerName(), "newName");

    }

    @Test
    void removeOrder() throws Exception{
        // Arrange
        String filePath = testDao.findOrderFileFromDir("08102021");
        testDao.loadAnOrderData(filePath);
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
        testDao.addOrder(newOrder);

        // Act
        testDao.removeOrder(orderNumber);
        Order retrievedOrder = testDao.getOrder(orderNumber);

        // Assert
        assertNull(retrievedOrder);

    }

    @Test
    void getAllOrder() throws Exception{
        // Arrange
        String filePath = testDao.findOrderFileFromDir("08102021");
        testDao.loadAnOrderData(filePath);
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
        testDao.addOrder(newOrder);

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
        String file = testDao.findOrderFileFromDir("08102021");

        // Assert
        assertEquals(
                (newFilePath).replace("/",""),
                file.replace("\\", ""));

        // Delete file
        newFile.delete();
    }

    @Test
    void getAllFilesTest() {
        List<String> allOrderFiles = testDao.getAllOrderFiles(testDao.ORDERDIR);
        System.out.println(allOrderFiles);
        assertEquals(allOrderFiles.size(),1);
    }

    @Test
    void writeFileTest() throws Exception{
        // Arrange
        String date = "08102021";  // Do not change this. this is made in @beforeEach.
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
        newOrder.setOrderDate(date);
        testDao.addOrder(newOrder);

        String date1 = "09122021";
        int orderNumber1 = 2;
        Order newOrder1 = new Order(orderNumber1);
        newOrder1.setCustomerName("Customer");
        newOrder1.setState("TX");
        newOrder1.setTaxRate(new BigDecimal("1.23"));
        newOrder1.setProductType("asdf");
        newOrder1.setArea(new BigDecimal("1.23"));
        newOrder1.setCostPerSquareFoot(new BigDecimal("123"));
        newOrder1.setLaborCostPerSquareFoot(new BigDecimal("123"));
        newOrder1.setMaterialCost(new BigDecimal("123"));
        newOrder1.setLaborCost(new BigDecimal("123"));
        newOrder1.setTax(new BigDecimal("123"));
        newOrder1.setTotal(new BigDecimal("1.23"));
        newOrder1.setOrderDate(date1);
        testDao.addOrder(newOrder1);

        int orderNumber2 = 3;
        Order newOrder2 = new Order(orderNumber2);
        newOrder2.setCustomerName("Customer");
        newOrder2.setState("TX");
        newOrder2.setTaxRate(new BigDecimal("1.23"));
        newOrder2.setProductType("asdf");
        newOrder2.setArea(new BigDecimal("1.23"));
        newOrder2.setCostPerSquareFoot(new BigDecimal("123"));
        newOrder2.setLaborCostPerSquareFoot(new BigDecimal("123"));
        newOrder2.setMaterialCost(new BigDecimal("123"));
        newOrder2.setLaborCost(new BigDecimal("123"));
        newOrder2.setTax(new BigDecimal("123"));
        newOrder2.setTotal(new BigDecimal("1.23"));
        newOrder2.setOrderDate(date1);
        testDao.addOrder(newOrder2);

        // Act
        testDao.saveAllOrderData();

        // Assert
        File orderDir = new File("testdata/Orders");
        File[] filelist = orderDir.listFiles();
        assertEquals(Arrays.stream(filelist).filter(f -> f.getName().contains(date)).collect(Collectors.toList()).size(), 1);
        assertEquals(Arrays.stream(filelist).filter(f -> f.getName().contains(date1)).collect(Collectors.toList()).size(), 1);
        assertEquals(2, filelist.length);
    }
}