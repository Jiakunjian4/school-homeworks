package org.example;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.example.TelephoneBilling.*;
import static org.junit.jupiter.api.Assertions.*;

class TelephoneBillingTest {
    TelephoneBilling billingService = new TelephoneBilling();

    // 等价类分析测试用例
    @Test
    void testCalculateBillUnder20Minutes() {
        LocalDateTime start = LocalDateTime.of(2024, 3, 12, 10, 0); // 假设通话开始时间
        LocalDateTime end = LocalDateTime.of(2024, 3, 12, 10, 19); // 假设通话结束时间，刚好19分钟
        double expectedBill = 0.05 * 20; // 由于不足20分钟按20分钟计费
        assertEquals(expectedBill, billingService.calculateBill(start, end), 0.001);

        // 测试正好20分钟的情况
        end = LocalDateTime.of(2024, 3, 12, 10, 20);
        assertEquals(1.00, billingService.calculateBill(start, end), 0.001);
    }

    @Test
    void testCalculateBillOver20Minutes() {
        LocalDateTime start = LocalDateTime.of(2024, 3, 12, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 3, 12, 11, 21); // 通话时间为1小时21分钟
        double expectedBill = 1.00 + (41 * 0.10); // 21分钟超过部分按每分钟0.10美元计费
        assertEquals(expectedBill, billingService.calculateBill(start, end), 0.001);
    }

    // 处理夏令时转换的特殊场景
    @Test
    void testCalculateBillDuringDaylightSavingSpringForward() {
        // 春季转换时，假设通话跨越了2:00 AM变为3:00 AM
        LocalDateTime start = LocalDateTime.of(2024, 3, 31, 1, 59); // 开始时间在夏令时调整前
        LocalDateTime end = LocalDateTime.of(2024, 3, 31, 3, 30); // 结束时间在夏令时调整后

        // 在处理夏令时转换时，确保通话时间被正确计算，不因时钟调整额外计费
        double actualBill = billingService.calculateBill(start, end);
        double expectedBillInStandardTime = Duration.between(start, end.minusHours(1)).toMinutes(); // 减去多出的1小时
        expectedBillInStandardTime = Math.ceil(expectedBillInStandardTime); // 向上进位到整分钟
        double expectedBill = applyBillingRate(expectedBillInStandardTime);
        assertEquals(expectedBill, actualBill, 0.001);
    }

    @Test
    void testCalculateBillDuringDaylightSavingFallBack() {
        // 秋季转换时，假设通话跨越了2:00 AM变为1:00 AM
        LocalDateTime start = LocalDateTime.of(2024, 11, 3, 1, 30); // 开始时间在夏令时调整前
        LocalDateTime end = LocalDateTime.of(2024, 11, 3, 2, 30); // 结束时间在夏令时调整后

        // 在处理夏令时转换时，确保通话时间被正确计算，不因时钟调整少计费
        double actualBill = billingService.calculateBill(start, end);
        double expectedBillInStandardTime = Duration.between(start, end.plusHours(1)).toMinutes(); // 加上多出的1小时
        expectedBillInStandardTime = Math.ceil(expectedBillInStandardTime); // 向上进位到整分钟
        double expectedBill = applyBillingRate(expectedBillInStandardTime);
        assertEquals(expectedBill, actualBill, 0.001);
    }

    // 辅助方法，用于根据通话时长应用费率
    private double applyBillingRate(double standardTimeMinutes) {
        if (standardTimeMinutes <= 20) {
            return standardTimeMinutes * 0.05;
        } else {
            return 1.00+ ((standardTimeMinutes - 20) *0.10);
        }
    }
}