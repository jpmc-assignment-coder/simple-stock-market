Feature:Super Simple Stock Market Test for Global Beverage Corporation Exchange

  Scenario: Calculate Dividend Yield
    Given Common stock example data
      | symbol | lastDividend | parValue |
      | TEA    | 0            | 100      |
      | POP    | 8            | 100      |
      | ALE    | 23           | 60       |
      | JOE    | 13           | 250      |
    And Preferred stock example data
      | symbol | lastDividend | parValue | fixedDividendPercentage |
      | GIN    | 8            | 100      | 2                       |
    When Calculating "Dividend Yield" for price
      | TEA | 50.5  |
      | POP | 4     |
      | ALE | 46.0  |
      | JOE | 3.250 |
      | GIN | 2.00  |
    Then Matches below result
      | TEA | 0.0 |
      | POP | 2.0 |
      | ALE | 0.5 |
      | JOE | 4.0 |
      | GIN | 1.0 |

  Scenario: Calculate PERatio
    Given Common stock example data
      | symbol | lastDividend | parValue |
      | TEA    | 0            | 100      |
      | POP    | 8            | 100      |
      | ALE    | 23           | 60       |
      | JOE    | 13           | 250      |
    And Preferred stock example data
      | symbol | lastDividend | parValue | fixedDividendPercentage |
      | GIN    | 8            | 100      | 2                       |
    When Calculating "PE Ratio" for price
      | TEA | 50.5  |
      | POP | 16.0  |
      | ALE | 11.5  |
      | JOE | 520.0 |
      | GIN | 2.00  |
    Then Matches below result
      | TEA | 0.0  |
      | POP | 2.0  |
      | ALE | 0.5  |
      | JOE | 40.0 |
      | GIN | 0.25 |

  Scenario: Calculate Volume Weighted Stock Price
  Only calculates for last 15 minutes of data
  timeStampInMillis column data in below is added to current milliseconds to record recent data
    Given Common stock example data
      | symbol | lastDividend | parValue |
      | ALE    | 23           | 60       |
      | JOE    | 13           | 250      |
    And Example trade data
      | timeStampInMillis | symbol | quantity | indicator | price |
      | -5000             | ALE    | 10       | BUY       | 2.0   |
      | -50000            | ALE    | 20       | BUY       | 5.0   |
      | -500000           | ALE    | 30       | BUY       | 1.0   |
      | -5000000          | JOE    | 10       | BUY       | 5.0   |
    When Calculating "Volume Weighted Stock Price" for stocks
      | ALE |
      | JOE |
    Then Matches below result
      | ALE | 2.5 |
      | JOE | 0.0 |

  Scenario: Calculate All Share Index Price
  Geometric mean of last recorded price per stock
    Given Common stock example data
      | symbol | lastDividend | parValue |
      | ALE    | 23           | 60       |
      | JOE    | 13           | 250      |
    And Example trade data
      | timeStampInMillis | symbol | quantity | indicator | price |
      | -5000000          | ALE    | 10       | BUY       | 2.0   |
      | -500000           | ALE    | 20       | BUY       | 5.0   |
      | -500              | ALE    | 30       | BUY       | 1.0   |
      | -5000000          | JOE    | 10       | BUY       | 5.0   |
    When Calculating "Index Price" for stocks
      | ALL |
    Then Matches below result
      | ALL | 2.23607 |
