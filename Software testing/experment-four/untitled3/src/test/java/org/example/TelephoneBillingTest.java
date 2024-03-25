package org.example;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TelephoneBillingTest {
    private final TelephoneBilling billingService = new TelephoneBilling();

    // 测试正常通话费用
    @Test
    void testNormalFeeCalculation() {
        ZonedDateTime start = ZonedDateTime.of(2024, 3, 25, 10, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime end = start.plusMinutes(15);

        BigDecimal calculatedFee = billingService.calculateFee(start, end);
        assertEquals(new BigDecimal("0.75"), calculatedFee);
    }

    // 处理跨越夏令时开始的测试，但Duration.between会自动处理夏令时变化
    @Test
    void testFeeCalculationDuringDstStart() {
        // 假设2024年3月的某个周日清晨2:00到3:00之间发生夏令时变化，时钟向前跳一个小时
        ZonedDateTime startBeforeDst = ZonedDateTime.of(2024, 3, 10, 2, 0, 0, 0, ZoneId.of("America/New_York")); // 夏令时开始前一分钟
        ZonedDateTime endAfterDst = ZonedDateTime.of(2024, 3, 10, 3, 30, 0, 0, ZoneId.of("America/New_York")); // 夏令时开始后一小时

        BigDecimal calculatedFee = billingService.calculateFee(startBeforeDst, endAfterDst);
        assertEquals(new BigDecimal("2.00"), calculatedFee); // 实际通话时间为30分钟
    }

    // 处理跨越夏令时结束的测试，但Duration.between会自动处理夏令时变化
    @Test
    void testFeeCalculationDuringDstEnd() {
        // 假设2024年11月的第一个周日清晨2:00到1:00之间发生非夏令时变化，时钟向后跳一个小时
        ZonedDateTime startBeforeDstEnd = ZonedDateTime.of(2024, 11, 3, 1, 30, 0, 0, ZoneId.of("America/New_York")); // 夏令时结束前半小时
        ZonedDateTime endAfterDstEnd = ZonedDateTime.of(2024, 11, 3, 3, 0, 0, 0, ZoneId.of("America/New_York")); // 夏令时结束后半小时

        BigDecimal calculatedFee = billingService.calculateFee(startBeforeDstEnd, endAfterDstEnd);
        assertEquals(new BigDecimal("14.00"), calculatedFee); // 实际通话时间为150分钟
    }

}