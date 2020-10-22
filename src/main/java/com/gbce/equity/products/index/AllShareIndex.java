package com.gbce.equity.products.index;

import com.gbce.equity.products.ProductStore;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Set;

public class AllShareIndex implements Index {

    private ProductStore productStore;

    public AllShareIndex(ProductStore productStore) {
        this.productStore = productStore;
    }

    public ProductStore getProductStore() {
        return productStore;
    }

    public Set<String> getStockSymbols() {
        return productStore.getStockSymbols();
    }

    public IndexType getIndexType() {
        return IndexType.ALL_SHARE;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("StockSymbols", getProductStore().getStockSymbols())
                .toString();
    }
}
