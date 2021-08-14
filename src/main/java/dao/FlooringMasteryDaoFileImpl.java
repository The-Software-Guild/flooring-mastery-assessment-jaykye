package dao;

import dto.Order;
import dto.Product;
import dto.Tax;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class FlooringMasteryDaoFileImpl implements FlooringMasteryDao{
    String BASEDATADIR;
    String ORDERDIR;
    String TAXPATH;
    String PRODUCTPATH;
    String BACKUPPATH;

    Map<Integer, Order> orderMap = new HashMap<>();
    Map<String, Tax> taxMap = new HashMap<>();
    Map<String, Product> productMap = new HashMap<>();
    String DELIMITER = "::";


    public FlooringMasteryDaoFileImpl() {
        BASEDATADIR = "data/";

        TAXPATH = BASEDATADIR + "Data/Taxes.txt";
        PRODUCTPATH = BASEDATADIR + "Data/Products.txt";
        ORDERDIR = BASEDATADIR + "Orders/";
        BACKUPPATH = BASEDATADIR + "Backup/DataExport.txt";

    }

    public FlooringMasteryDaoFileImpl(String dataDir) {
        BASEDATADIR = dataDir;

        TAXPATH = BASEDATADIR + "Data/Taxes.txt";
        PRODUCTPATH = BASEDATADIR + "Data/Products.txt";
        ORDERDIR = BASEDATADIR + "Orders/";
        BACKUPPATH = BASEDATADIR + "Backup/DataExport.txt";

    }

    @Override
    public Order addOrder(Order order) {
        return  orderMap.put(order.getOrderNumber(), order);
    }

    @Override
    public Order editOrder(int orderNumber, int fieldNumber, String newValue) {
        Order order = orderMap.get(orderNumber);

        // Remember that I cannot set the calculated fields, dependent fields and order date(Cannot be changed.)
        if (order != null) {  // if I can find the file...
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
        }
        return order; // if the order is null, just return it. This will be handled in service layer with others.
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

    @Override
    public void loadAllData() throws FlooringMasteryPersistenceException{
        // opens Tax, Product, Orders
        loadAllOrderData();
        loadTaxFile();
        loadProductFile();
    }

    // :::::::::::::txt file specific methods:::::::::::::::::

    // Tax file
    @Override
    public Tax getTax(String state) {
        return taxMap.get(state);
    }

    private Tax unmarshallTax(String taxEntryText) {
        String[] taxTokens = taxEntryText.split(DELIMITER);
        return new Tax(taxTokens[0], taxTokens[1], new BigDecimal(taxTokens[2]));
    }

    private void loadTaxFile() throws FlooringMasteryPersistenceException{
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(TAXPATH)));
        }
        catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not load tax data into memory.", e);
        }

        String currentLine;
        Tax currentTax;
        // Skip header
        currentLine = scanner.nextLine();

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTax = unmarshallTax(currentLine);
            taxMap.put(currentTax.getStateAbbreviation(), currentTax);
        }

        // close scanner
        scanner.close();
    }

    // Product file
    @Override
    public Product getProduct(String productType) {
        return productMap.get(productType);
    }

    private Product unmarshallProduct(String productEntryText) {
        String[] productTokens = productEntryText.split(DELIMITER);
        return new Product(productTokens[0],  new BigDecimal(productTokens[1]), new BigDecimal(productTokens[2]));
    }

    private void loadProductFile() throws FlooringMasteryPersistenceException{
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(PRODUCTPATH)));
        }
        catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not load product data into memory.", e);
        }

        String currentLine;
        Product currentProduct;
        // Skip header
        currentLine = scanner.nextLine();

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = unmarshallProduct(currentLine);
            productMap.put(currentProduct.getProductType(), currentProduct);
        }

        // close scanner
        scanner.close();
    }

    // Order file

    public List<String>  getAllOrderFiles(String orderDir){
        List<String> orderFiles = new ArrayList<String>();
        File orderDirObj = new File(orderDir);  // create new file instance.
        File[] fileList;

        try {
            fileList = orderDirObj.listFiles();
            if (fileList != null) {
                orderFiles = Arrays.stream(fileList).filter(
                        path -> path.getName().startsWith("Orders_")).map(
                                file -> file.toString()).collect(Collectors.toList());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Unable to find the file.");
        }

        return orderFiles;
    }

    public String findOrderFileFromDir(String date){
        String orderFilePathString ="";
        File orderDir = new File(ORDERDIR);  // create new file instance.
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

    public String findOrderFileFromList(List<String> filePathList, String date)
            throws FlooringMasteryPersistenceException {

        String orderFilePathString ="";
        File orderDir = new File(ORDERDIR);  // create new file instance.

        Optional<String> fileContainer = filePathList.stream().filter(
                fileString -> fileString.contains(date)).findFirst();

        if (fileContainer.isPresent()){
            orderFilePathString = fileContainer.get();
        }

        else {
            throw new FlooringMasteryPersistenceException("Cannot find file for date : " + date);
        }

        return orderFilePathString;
    }

    private void loadAllOrderData() throws FlooringMasteryPersistenceException{
        List<String> orderFileList = getAllOrderFiles(ORDERDIR);
        for (String orderFilePath : orderFileList) {
            loadAnOrderData(orderFilePath);
        }
    }

    public void loadAnOrderData(String filePath) throws FlooringMasteryPersistenceException{
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

        // The first line is a header. -- skip one line.
        currentLine = scanner.nextLine();

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentOrder = unmarshallOrder(currentLine);
            orderMap.put(currentOrder.getOrderNumber(), currentOrder);
        }

        // close scanner
        scanner.close();
    }

    @Override
    public void saveAllOrderData() throws FlooringMasteryPersistenceException{
        // Dumps all data in orderMap into appropriate files.

        // Hacky way of handling orderDates that got removed. -- file must be deleted.
        File orderDir = new File(ORDERDIR);
        File[] filelist = orderDir.listFiles();
        for (File file : filelist) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }

        PrintWriter out;
        String orderAsText;
        List<Order> orderList = getAllOrder();

        // Group orders by the order date.
        Map<String, List<Order>> fileMap = orderList.stream().collect(Collectors.groupingBy(order -> order.getOrderDate()));
        for (String date : fileMap.keySet()) {
            List<Order> orderListPerDate = fileMap.get(date);

            // Write to an individual order file.
            String filename = "Orders_" + date + ".txt";
            String filepath = ORDERDIR + filename;

            // Open file.
            try {
                out = new PrintWriter(new FileWriter(filepath));
            } catch (IOException e) {
                throw new FlooringMasteryPersistenceException(
                        "Could not save order data.", e);
            }

            // Write header.
            String header = "OrderNumber::CustomerName::State::TaxRate::ProductType" +
                    "::Area::CostPerSquareFoot::LaborCostPerSquareFoot" +
                    "::MaterialCost::LaborCost::Tax::Total::OrderDate";
            out.println(header);

            // Write records.
            for (Order currentOrder : orderListPerDate) {
                orderAsText = marshallOrder(currentOrder);
                out.println(orderAsText);
                // force PrintWriter to write line to the file
                out.flush();
            }

            // Clean up
            out.close();
        }
    }

    @Override
    public void exportToMasterOrderData() throws FlooringMasteryPersistenceException {
        PrintWriter out;
        String orderAsText;
        List<Order> orderList = getAllOrder();
        String filepath = BACKUPPATH;

        // Open file.
        try {
            out = new PrintWriter(new FileWriter(filepath));
        }
        catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not save to backup file.", e);
        }

        // Write header.
        String header = "OrderNumber::CustomerName::State::TaxRate::ProductType" +
                "::Area::CostPerSquareFoot::LaborCostPerSquareFoot" +
                "::MaterialCost::LaborCost::Tax::Total::OrderDate";
        out.println(header);

        for (Order order : orderList) {
            // Write records.
            orderAsText = marshallOrder(order);
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
        orderAsText += anOrder.getTotal() + DELIMITER;
        orderAsText += anOrder.getOrderDate();

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
        orderFromFile.setTotal(new BigDecimal(orderTokens[i++]));
        orderFromFile.setOrderDate(orderTokens[i]);

        // Don't mind the calculated fields here.
        // Calculation will be done with a separate method in service layer.
        return orderFromFile;
    }

}
