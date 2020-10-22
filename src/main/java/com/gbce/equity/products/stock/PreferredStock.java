package com.gbce.equity.products.stock;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

public class PreferredStock extends BaseStock {

    private BigDecimal fixedDividendPercentage;

    public PreferredStock(String symbol, BigDecimal lastDividend, BigDecimal parValue,
                          BigDecimal fixedDividendPercentage) {
        super(symbol, lastDividend, parValue);
        setFixedDividendPercentage(fixedDividendPercentage);
    }

    public BigDecimal getFixedDividendPercentage() {
        return fixedDividendPercentage;
    }

    public void setFixedDividendPercentage(BigDecimal fixedDividendPercentage) {
        this.fixedDividendPercentage = fixedDividendPercentage;
    }

    public BigDecimal getFixedDividendDecimal() {
        return getFixedDividendPercentage().divide(new BigDecimal(100));
    }

    public StockType getStockType() {
        return StockType.PREFERRED;
    }

    public BigDecimal getDividendForDividendYield() {
        return getFixedDividendDecimal().multiply(getParValue());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
                .appendSuper(super.toString())
                .append("fixedDividendPercentage", fixedDividendPercentage)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
