package dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Order {
    private int orderNumber;
    private String CustomerName;
    private String State;
    private BigDecimal TaxRate;
    private String ProductType;
    private BigDecimal Area;
    private BigDecimal CostPerSquareFoot;
    private BigDecimal LaborCostPerSquareFoot;
    private BigDecimal MaterialCost;
    private BigDecimal LaborCost;
    private BigDecimal Tax;
    private BigDecimal Total;

    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public String getState() {
        return State;
    }

    public BigDecimal getTaxRate() {
        return TaxRate;
    }

    public String getProductType() {
        return ProductType;
    }

    public BigDecimal getArea() {
        return Area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return CostPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return LaborCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return MaterialCost;
    }

    public BigDecimal getLaborCost() {
        return LaborCost;
    }

    public BigDecimal getTax() {
        return Tax;
    }

    public BigDecimal getTotal() {
        return Total;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public void setState(String state) {
        State = state;
    }

    public void setTaxRate(BigDecimal taxRate) {
        TaxRate = taxRate;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public void setArea(BigDecimal area) {
        Area = area;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        CostPerSquareFoot = costPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        LaborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        MaterialCost = materialCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        LaborCost = laborCost;
    }

    public void setTax(BigDecimal tax) {
        Tax = tax;
    }

    public void setTotal(BigDecimal total) {
        Total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderNumber == order.orderNumber && Objects.equals(CustomerName, order.CustomerName) && Objects.equals(State, order.State) && Objects.equals(TaxRate, order.TaxRate) && Objects.equals(ProductType, order.ProductType) && Objects.equals(Area, order.Area) && Objects.equals(CostPerSquareFoot, order.CostPerSquareFoot) && Objects.equals(LaborCostPerSquareFoot, order.LaborCostPerSquareFoot) && Objects.equals(MaterialCost, order.MaterialCost) && Objects.equals(LaborCost, order.LaborCost) && Objects.equals(Tax, order.Tax) && Objects.equals(Total, order.Total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, CustomerName, State, TaxRate, ProductType, Area, CostPerSquareFoot, LaborCostPerSquareFoot, MaterialCost, LaborCost, Tax, Total);
    }
}
