package com.gbce.equity.trading.stock;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Stock Trade Implementation
 */
public class StockTrade implements Trade {
    private long timeStampInMillis;
    private String symbol;
    private int quantity;
    private Indicator indicator;
    private BigDecimal price;

    public StockTrade(long timeStampInMillis, String symbol, int quantity, Indicator indicator, BigDecimal price) {
        this.timeStampInMillis = timeStampInMillis;
        this.symbol = symbol;
        this.quantity = quantity;
        this.indicator = indicator;
        this.price = price;
    }

    public long getTimeStampInMillis() {
        return timeStampInMillis;
    }

    public void setTimeStampInMillis(long timeStampInMillis) {
        this.timeStampInMillis = timeStampInMillis;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
                .append("timeStampInMillis", new Date(timeStampInMillis).toInstant().toString())
                .append("symbol", symbol)
                .append("quantity", quantity)
                .append("indicator", indicator)
                .append("price", price)
                .toString();
    }
}
