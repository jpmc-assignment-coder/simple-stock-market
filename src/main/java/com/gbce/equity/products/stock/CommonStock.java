package com.gbce.equity.products.stock;

import java.math.BigDecimal;

public class CommonStock extends BaseStock {

    public CommonStock(String symbol, BigDecimal lastDividend, BigDecimal parValue) {
        super(symbol, lastDividend, parValue);
    }

    public StockType getStockType() {
        return StockType.COMMON;
    }

    public BigDecimal getDividendForDividendYield() {
        return getLastDividend();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
