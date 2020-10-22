package com.gbce.equity.products;

import com.gbce.equity.products.index.Index;
import com.gbce.equity.products.index.IndexType;
import com.gbce.equity.products.stock.Stock;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Product Store/ Repository Interface
 */
public interface ProductStore {
    Set<String> getStockSymbols();

    Stock getStock(String symbol);

    void addStock(Stock stock);

    Index getIndex(IndexType indexType);

    Stock createCommonStock(String symbol, BigDecimal lastDividend, BigDecimal parValue);

    Stock createPreferredStock(String symbol, BigDecimal lastDividend, BigDecimal parValue,
                               BigDecimal fixedDividendPercentage);

    Index createAllShareIndex();

    String toProductsString();
}
