import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    @Test
    @DisplayName("输入错误")
    void parameters_error_test() {
        Triangle triangle = new Triangle();
        String type = triangle.classify(0, 4, 5);
        assertEquals("输入错误", type);
    }
    @Test
    @DisplayName("不等边三角形")
    void scalene_test() {
        Triangle triangle = new Triangle();
        String type = triangle.classify(3, 4, 6);
        assertEquals("不等边三角形", type);
    }
    @Test
    @DisplayName("非三角形")
    void no_triangular_test() {
        Triangle triangle = new Triangle();
        String type = triangle.classify(6, 4, 10);
        assertEquals("非三角形", type);
    }
    @Test
    @DisplayName("等边三角形")
    void equilateral_test() {
        Triangle triangle = new Triangle();
        String type = triangle.classify(6, 6, 6);
        assertEquals("等边三角形", type);
    }
    @Test
    @DisplayName("等腰三角形")
    void isosceles_test() {
        Triangle triangle = new Triangle();
        String type = triangle.classify(6, 6, 8);
        assertEquals("等腰三角形", type);
    }



    @Test//一般边界值方法
    void testClassifyWithGeneralBoundaryValues() {
        Triangle triangle = new Triangle();

        // 测试等边三角形
        assertEquals("等边三角形", triangle.classify(10, 10, 10));

        // 测试等腰三角形
        assertEquals("等腰三角形", triangle.classify(10, 10, 9));

        // 测试不等边三角形
        assertEquals("不等边三角形", triangle.classify(5, 6, 7));

        // 测试边界值：两边之和等于第三边，应该返回非三角形
        assertEquals("非三角形", triangle.classify(50, 50, 100));

        // 测试边界值：最小的合法边长
        assertEquals("等腰三角形", triangle.classify(2, 2, 1));

        // 测试边界值：最大的合法边长
        assertEquals("等边三角形", triangle.classify(100, 100, 100));
    }

    @Test//健壮性边界值方法
    void testClassifyWithRobustBoundaryValues() {
        Triangle triangle = new Triangle();

        // 测试非法输入：小于最小边长
        assertEquals("输入错误", triangle.classify(0, 5, 5));

        // 测试非法输入：大于最大边长
        assertEquals("输入错误", triangle.classify(101, 101, 101));

        // 测试非法输入：负值
        assertEquals("输入错误", triangle.classify(-1, -1, -1));
    }

    @Test//最坏情况一般边界值分析方法
    void testClassifyWithWorstCaseGeneralBoundaryValues() {
        Triangle triangle = new Triangle();

        // 测试最大差值的不等边三角形
        assertEquals("非三角形", triangle.classify(1, 2, 99));

        // 测试接近非三角形条件的三角形
        assertEquals("等腰三角形", triangle.classify(99, 99, 100));

        // 测试接近最大合法边长的三角形
        assertEquals("等边三角形", triangle.classify(99, 99, 99));
    }
    @Test//最坏情况健壮性边界值分析方法
    public void testClassifyWithWorstCaseRobustBoundaryValues() {
        Triangle triangle = new Triangle();

        // 最小值减去1的情况
        assertEquals("输入错误", triangle.classify(0, 1, 1));
        // 最大值加上1的情况
        assertEquals("输入错误", triangle.classify(101, 100, 100));
        // 最小值附近接近非三角形的情况
        assertEquals("非三角形", triangle.classify(4, 2, 1));
        // 最大值附近接近非三角形的情况
        assertEquals("输入错误", triangle.classify(99, 99, 197));
    }

}