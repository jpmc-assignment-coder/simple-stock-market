package com.gbce.equity.products.index;

import java.util.Set;

public interface Index {
    Set<String> getStockSymbols();

    IndexType getIndexType();
}
