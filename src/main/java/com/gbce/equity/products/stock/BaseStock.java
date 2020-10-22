package com.gbce.equity.products.stock;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

public abstract class BaseStock implements Stock {

    private String symbol;
    private BigDecimal lastDividend;
    private BigDecimal parValue;

    public BaseStock(String symbol, BigDecimal lastDividend, BigDecimal parValue) {
        this.symbol = symbol;
        this.lastDividend = lastDividend;
        this.parValue = parValue;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getLastDividend() {
        return lastDividend;
    }

    public void setLastDividend(BigDecimal lastDividend) {
        this.lastDividend = lastDividend;
    }

    public BigDecimal getParValue() {
        return parValue;
    }

    public void setParValue(BigDecimal parValue) {
        this.parValue = parValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BaseStock baseStock = (BaseStock) o;

        return new EqualsBuilder()
                .append(symbol, baseStock.symbol)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(symbol)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
                .append("stockType", getStockType())
                .append("symbol", symbol)
                .append("lastDividend", lastDividend)
                .append("parValue", parValue)
                .toString();
    }
}
