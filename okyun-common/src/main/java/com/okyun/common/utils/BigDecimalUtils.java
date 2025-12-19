package com.okyun.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;

public class BigDecimalUtils {

    /**
     * 100
     */
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    /**
     * 处理 BigDecimal 可能的 null 值，避免 NullPointerException，提高计算效率
     */
    public static BigDecimal safeValue(BigDecimal value) {
        return value != null ? value.setScale(2, RoundingMode.HALF_UP) : ZERO;
    }

    // 如果必须处理其他数值类型，添加安全转换方法
    public static BigDecimal safeValue(Double value) {
        return value != null ? BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP) : ZERO;
    }

    public static BigDecimal safeValue(Integer value) {
        return value != null ? new BigDecimal(value).setScale(2, RoundingMode.HALF_UP) : ZERO;
    }

    /**
     * 保障折扣率在 0 - 1 之间，保留1位小数
     */
    public static BigDecimal safeRate(BigDecimal rateVale) {
        return Optional.ofNullable(rateVale)
                .map(rate -> rate.divide(HUNDRED, 3, RoundingMode.HALF_UP))
                .filter(rate -> rate.compareTo(ZERO) >= 0 && rate.compareTo(BigDecimal.ONE) <= 0)
                .orElse(ZERO);
    }
}
