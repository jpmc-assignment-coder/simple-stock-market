package com.gbce.equity.products;

import com.gbce.equity.products.index.AllShareIndex;
import com.gbce.equity.products.index.Index;
import com.gbce.equity.products.index.IndexType;
import com.gbce.equity.products.stock.CommonStock;
import com.gbce.equity.products.stock.PreferredStock;
import com.gbce.equity.products.stock.Stock;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Trade Store/ Repository In Memory Implementation
 */
public class ProductStoreInMemImpl implements ProductStore {

    private final Map<String, Stock> stockCache = new ConcurrentHashMap<>();
    private final Map<IndexType, Index> indexCache = new ConcurrentHashMap<>();

    public Set<String> getStockSymbols() {
        return stockCache.keySet();
    }

    public Stock getStock(String symbol) {
        return stockCache.get(symbol);
    }

    @Override
    public void addStock(Stock stock) {
        stockCache.put(stock.getSymbol(), stock);
    }

    public Index getIndex(IndexType indexType) {
        return indexCache.get(indexType);
    }

    public Stock createCommonStock(String symbol, BigDecimal lastDividend, BigDecimal parValue) {
        Stock stock = new CommonStock(symbol, lastDividend, parValue);
        stockCache.put(symbol, stock);
        return stock;
    }

    public Stock createPreferredStock(String symbol, BigDecimal lastDividend, BigDecimal parValue,
                                      BigDecimal fixedDividendPercentage) {
        Stock stock = new PreferredStock(symbol, lastDividend, parValue, fixedDividendPercentage);
        stockCache.put(symbol, stock);
        return stock;
    }

    public Index createAllShareIndex() {
        Index index = new AllShareIndex(this);
        indexCache.put(IndexType.ALL_SHARE, index);
        return index;
    }

    @Override
    public String toProductsString() {
        return stockCache.values().stream()
                .map(Stock::toString)
                .collect(Collectors.joining("\n"));
    }
}
