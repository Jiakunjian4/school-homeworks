package org.example;

import java.time.LocalDateTime;
import java.time.Duration;
public class TelephoneBilling {
    double RATE_FIRST_20_MINUTES = 0.05;
    double RATE_AFTER_20_MINUTES = 0.10;
    double FLAT_FEE_OVER_20_MINUTES = 1.00;

    /**
     * 根据通话起止时间计算账单金额
     * @param startTime 通话开始时间
     * @param endTime 通话结束时间
     * @return 账单金额（美元）
     */
    public double calculateBill(LocalDateTime startTime, LocalDateTime endTime) {
        Duration callDuration = Duration.between(startTime, endTime);
        long minutes = callDuration.toMinutes(); // 将通话时间转换为分钟

        // 不足1分钟按1分钟计算
        if (callDuration.getSeconds() % 60 != 0) {
            minutes++;
        }

        // 计算账单金额
        double bill;
        if (minutes <= 20) {
            bill = minutes * RATE_FIRST_20_MINUTES;
        } else {
            bill = FLAT_FEE_OVER_20_MINUTES + ((minutes - 20) * RATE_AFTER_20_MINUTES);
        }

        return bill;
    }
}
