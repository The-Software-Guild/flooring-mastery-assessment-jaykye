package dto;

import java.math.BigDecimal;

public class Tax {
    private String stateAbbreviation;
    private String StateName;
    private BigDecimal TaxRate;

    public Tax(String stateAbbreviation, String stateName, BigDecimal taxRate) {
        this.stateAbbreviation = stateAbbreviation;
        StateName = stateName;
        TaxRate = taxRate;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public String getStateName() {
        return StateName;
    }

    public BigDecimal getTaxRate() {
        return TaxRate;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public void setTaxRate(BigDecimal taxRate) {
        TaxRate = taxRate;
    }
}
