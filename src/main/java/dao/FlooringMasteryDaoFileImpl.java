package dao;

import dto.Order;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class FlooringMasteryDaoFileImpl implements FlooringMasteryDao{
    String BASEDATADIR;
    String ORDERDIR;
    String DATADIR;
    Map<Integer, Order> orderMap = new HashMap<>();
    String DELIMITER = ",";


    public FlooringMasteryDaoFileImpl() {
        BASEDATADIR = "data/";
        DATADIR = BASEDATADIR + "Data/";
        ORDERDIR = BASEDATADIR + "Orders/";

    }

    public FlooringMasteryDaoFileImpl(String dataDir) {
        BASEDATADIR = dataDir;
        DATADIR = BASEDATADIR + "Data/";
        ORDERDIR = BASEDATADIR + "Orders/";


    }

    @Override
    public Order addOrder(int orderNumber, Order order) {
        return orderMap.put(orderNumber, order);
    }

    @Override
    public Order editOrder(int orderNumber, int fieldNumber, String newValue) {
        Order order = orderMap.get(orderNumber);

        if (order != null) {
            switch (fieldNumber) {
                case 1:
                    order.setCustomerName(newValue);
                    break;
                case 2:
                    order.setState(newValue);
                    break;
                case 3:
                    order.setTaxRate(new BigDecimal(newValue));
                    break;
                case 4:
                    order.setProductType(newValue);
                    break;
                case 5:
                    order.setArea(new BigDecimal(newValue));
                    break;
                case 6:
                    order.setCostPerSquareFoot(new BigDecimal(newValue));
                    break;
                case 7:
                    order.setLaborCostPerSquareFoot(new BigDecimal(newValue));
                    break;
                case 8:
                    order.setMaterialCost(new BigDecimal(newValue));
                    break;
                case 9:
                    order.setLaborCost(new BigDecimal(newValue));
                    break;
                case 10:
                    order.setTax(new BigDecimal(newValue));
                    break;
                case 11:
                    order.setTotal(new BigDecimal(newValue));
                    break;
            }
        }
        return order;
    }

    @Override
    public Order removeOrder(int orderNumber) {
        return orderMap.remove(orderNumber);
    }

    @Override
    public Order getOrder(int orderId) {
        return orderMap.get(orderId);
    }

    @Override
    public List<Order> getAllOrder() {
        return new ArrayList<Order>(orderMap.values());

    }

    // :::::::::::::txt file specific methods:::::::::::::::::

    public String findOrderFile(String date){
        String orderFilePathString ="";
        File orderDir = new File(ORDERDIR);
        File[] fileList;

        try {
            fileList = orderDir.listFiles();
            if (fileList != null) {
                Optional<File> fileContainer = Arrays.stream(fileList).filter(
                        path -> path.getName().contains(date)).findFirst();
                if (fileContainer.isPresent()){
                    orderFilePathString = fileContainer.get().toString();
                }
                else {
                    System.out.println("Unable to find the file.");
                    for (File f : orderDir.listFiles()) {
                        System.out.println(f.getName());
                    }
                }
            }
            else {
                System.out.println("Unable to find the file.");
            }
        }
        catch (Exception e){
            System.out.println("Unable to find the file.");
        }

        return orderFilePathString;
    }


    public void loadData(String filePath) throws FlooringMasteryPersistenceException{
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(filePath)));
        }
        catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not load order data into memory.", e);
        }

        String currentLine;
        Order currentOrder;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentOrder = unmarshallOrder(currentLine);
            orderMap.put(currentOrder.getOrderNumber(), currentOrder);
        }

        // close scanner
        scanner.close();
    }

    public void writeData(String filePath) throws FlooringMasteryPersistenceException{
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(filePath));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not save order data.", e);
        }

        String orderAsText;
        List<Order> orderList = getAllOrder();
        for (Order currentOrder : orderList) {
            orderAsText = marshallOrder(currentOrder);
            out.println(orderAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
        out.close();
    }

    private String marshallOrder(Order anOrder) {

        String orderAsText = anOrder.getOrderNumber() + DELIMITER;
        orderAsText += anOrder.getCustomerName() + DELIMITER;
        orderAsText += anOrder.getState() + DELIMITER;
        orderAsText += anOrder.getTaxRate() + DELIMITER;
        orderAsText += anOrder.getProductType() + DELIMITER;
        orderAsText += anOrder.getArea() + DELIMITER;
        orderAsText += anOrder.getCostPerSquareFoot() + DELIMITER;
        orderAsText += anOrder.getLaborCostPerSquareFoot() + DELIMITER;
        orderAsText += anOrder.getMaterialCost() + DELIMITER;
        orderAsText += anOrder.getLaborCost() + DELIMITER;
        orderAsText += anOrder.getTax() + DELIMITER;
        orderAsText += anOrder.getTotal();

        return orderAsText;
    }
    
    private Order unmarshallOrder(String orderEntryText) {

        String[] orderTokens = orderEntryText.split(DELIMITER);
        int i = 0;
        int orderId = Integer.parseInt(orderTokens[i++]);

        Order orderFromFile = new Order(orderId);

        orderFromFile.setCustomerName(orderTokens[i++]);
        orderFromFile.setState(orderTokens[i++]);
        orderFromFile.setTaxRate(new BigDecimal(orderTokens[i++]));
        orderFromFile.setProductType(orderTokens[i++]);
        orderFromFile.setArea(new BigDecimal(orderTokens[i++]));
        orderFromFile.setCostPerSquareFoot(new BigDecimal(orderTokens[i++]));
        orderFromFile.setLaborCostPerSquareFoot(new BigDecimal(orderTokens[i++]));
        orderFromFile.setMaterialCost(new BigDecimal(orderTokens[i++]));
        orderFromFile.setLaborCost(new BigDecimal(orderTokens[i++]));
        orderFromFile.setTax(new BigDecimal(orderTokens[i++]));
        orderFromFile.setTotal(new BigDecimal(orderTokens[i]));

        // Don't mind the calculated fields here.
        // Calculation will be done with a separate method in service layer.
        return orderFromFile;
    }

}
