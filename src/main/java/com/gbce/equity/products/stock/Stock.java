package com.gbce.equity.products.stock;

import java.math.BigDecimal;

public interface Stock {

    String getSymbol();

    StockType getStockType();

    /**
     * Method to abstract dividend logic used for common/preferred stock dividend yield calculation
     */
    BigDecimal getDividendForDividendYield();

    BigDecimal getLastDividend();

    BigDecimal getParValue();
}
