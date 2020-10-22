package com.gbce.equity.trading.stock;

import java.math.BigDecimal;

/**
 * Trade Interface
 */
public interface Trade {
    long getTimeStampInMillis();

    String getSymbol();

    int getQuantity();

    Indicator getIndicator();

    BigDecimal getPrice();

}
