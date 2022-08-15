package book;

import static java.lang.Math.*;

/******************************
 * 用途说明: @Description 数学函数练习
 * 作者姓名: @author Administrator
 * 创建时间: @date 2022-08-10 20:41
 ******************************/
public class MathTest {
    public static void main(String[] args) {
        double x = 4;
        System.out.println("sqrt(x) = " + sqrt(x));//平方根
        System.out.println("pow(x,2) = " + pow(x, 2));//幂运算
        System.out.println("abs(x) = " + abs(x));//绝对值
        System.out.println("E = " + E);
        System.out.println("PI = " + PI);
        System.out.println("acos(x) = " + acos(x));//
        System.out.println("asin(x) = " + asin(x));//
        System.out.println("atan(x) = " + atan(x));//
        System.out.println("atan2(x,2.0) = " + atan2(x, 2.0));//
        System.out.println("cbrt(x) = " + cbrt(x));//
        System.out.println("ceil(x) = " + ceil(x));//
        System.out.println("copySign(x,2.0) = " + copySign(x, 2.0));//
        System.out.println("cos(x) = " + cos(x));//
        System.out.println("cosh(x) = " + cosh(x));//
        System.out.println("exp(x) = " + exp(x));//
        System.out.println("expm1(x) = " + expm1(x));//
        System.out.println("floor(x) = " + floor(x));//
        System.out.println("getExponent(x) = " + getExponent(x));//
        System.out.println("hypot(x,2.0) = " + hypot(x, 2.0));//
        System.out.println("IEEEremainder(x,2.0) = " + IEEEremainder(x, 2.0));//
        System.out.println("log(x) = " + log(x));//
        System.out.println("log1p(x) = " + log1p(x));//
        System.out.println("log10(x) = " + log10(x));//
        System.out.println("max(x,2.0) = " + max(x, 2.0));//
        System.out.println("min(x,2.0) = " + min(x, 2.0));//
        System.out.println("nextAfter(x,2.0) = " + nextAfter(x, 2.0));//
        System.out.println("nextUp(x) = " + nextUp(x));//
        System.out.println("nextDown(x) = " + nextDown(x));//
        System.out.println("random() = " + random());//
        System.out.println("rint(x) = " + rint(x));//
        System.out.println("round(x) = " + round(x));//
        System.out.println("scalb(x,2) = " + scalb(x, 2));//
        System.out.println("signum(x) = " + signum(x));//
        System.out.println("sin(x) = " + sin(x));//
        System.out.println("sinh(x) = " + sinh(x));//
        System.out.println("tan(x) = " + tan(x));//
        System.out.println("tanh(x) = " + tanh(x));//
        System.out.println("toDegrees(x) = " + toDegrees(x));//
        System.out.println("toRadians(x) = " + toRadians(x));//
        System.out.println("ulp(x) = " + ulp(x));//
        x += 2;
        int a = 2;
        a += 2.5;
        System.out.println("x = " + x);
        System.out.println("a = " + a);

    }
}
