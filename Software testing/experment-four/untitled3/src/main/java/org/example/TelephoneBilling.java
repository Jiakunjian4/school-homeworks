package org.example;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.ZonedDateTime;

public class TelephoneBilling {
    public BigDecimal calculateFee(ZonedDateTime startTime, ZonedDateTime endTime) {
        long callDurationInMinutes = Duration.between(startTime, endTime).toMinutes();//通话时长

        // 将不足1分钟的通话视为1分钟
        if (callDurationInMinutes < 1) {
            callDurationInMinutes = 1;
        }

        // 计费逻辑
        BigDecimal fee;//通话费用
        if (callDurationInMinutes <= 20) {
            fee = BigDecimal.valueOf(callDurationInMinutes).multiply(BigDecimal.valueOf(0.05));
        } else {
            fee = BigDecimal.valueOf(1.00)
                    .add(BigDecimal.valueOf((callDurationInMinutes - 20)).multiply(BigDecimal.valueOf(0.10)));
        }

        return fee.setScale(2, RoundingMode.HALF_UP); // 保留两位小数并四舍五入
    }

}
