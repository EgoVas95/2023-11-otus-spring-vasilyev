package ru.otus.hw.mealconfigurator.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class RoundBigDecimals {

    public String roundAndPrepare(BigDecimal bigDecimal) {
        return round(bigDecimal).toPlainString();
    }

    public BigDecimal round(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return BigDecimal.ZERO;
        }
        return bigDecimal.setScale(1, RoundingMode.UP);
    }
}
