package book;

/******************************
 * 用途说明: @Description 位运算
 * 与（AND，&）：全1为1，有0则0   特殊用法：清零（与0进行与运算）、取一个数中的指定位（与1进行与运算）
 * 或（OR，|）：有1则1，全0为0    特殊用法：使特定数位为1
 * 异或（XOR，^）：不同为1，相同为0  特殊用法：使特定数位翻转（与1异或）、保留原值（与0异或）、交换两个变量的值
 * 非（NOT，~）：取反
 * 左移运算：value<<num
 *      数值value向左移动num位，左边二进制位丢弃，右边补0。（byte和short类型移位运算时会变成int型，结果要强制转换）
 *      若1被移位到最左侧，则变成负数，左移时舍弃位不包含1，则左移一次，相当于乘2
 * 右移运算：value>>num  数值value向右移动num位，正数左补0，负数左补1，右边舍弃。（即保留符号位）
 *      右移一次，相当于除以2，并舍弃余数，无符号右移>>>：左边位用0补充，右边丢弃
 *      负数以其正值的补码形式表示
 *      原码：一个整数按照绝对值大小转换成的二进制数称为原码
 *      反码：将二进制按位取反，所得的新二进制数称为原二进制数的反码
 *      补码：反码加1称为补码
 * 运算顺序 先& 后^ 后|
 * 进制的转换
 *      十进制转换成其他进制
 *          Integer.toHexString(i);//十六进制
 *          Integer.toOctalString(i);//八进制
 *          Integer.toBinaryString(i);//二进制
 *      其他进制转化为十进制
 *          Integer.valueOf("fff0",16);
 *          Integer.valueOf("123",8);
 *          Integer.valueOf("0101",2);
 * 作者姓名: @author Administrator
 * 创建时间: @date 2022-08-10 21:19
 ******************************/
public class BitOperation {

    public static void main(String[] args) {
        int a = 24;
        int b = 16;
        int c = Integer.parseInt(Integer.toHexString(a));
        int d = Integer.parseInt(Integer.toHexString(b));
        System.out.println("十进制转换为十六进制 = " + c);
        System.out.println("十进制转换为十六进制 = " + d);
        System.out.println("十六进制转换为十进制 = " + Integer.valueOf(String.valueOf(c), 16));
        System.out.println("十六进制转换为十进制 = " + Integer.valueOf(String.valueOf(d), 16));
        c = Integer.parseInt(Integer.toOctalString(a));
        d = Integer.parseInt(Integer.toOctalString(b));
        System.out.println("十进制转换为八进制 = " + c);
        System.out.println("十进制转换为八进制 = " + d);
        System.out.println("八进制转换为十进制 = " + Integer.valueOf(String.valueOf(c), 8));
        System.out.println("八进制转换为十进制 = " + Integer.valueOf(String.valueOf(d), 8));
        c = Integer.parseInt(Integer.toBinaryString(a));
        d = Integer.parseInt(Integer.toBinaryString(b));
        System.out.println("十进制转换为二进制 = " + c);
        System.out.println("十进制转换为二进制 = " + d);
        System.out.println("二进制转换为十进制 = " + Integer.valueOf(String.valueOf(c), 2));
        System.out.println("八进制转换为十进制 = " + Integer.valueOf(String.valueOf(d), 2));
        System.out.println("a & b = " + (a & b));
        System.out.println("a | b = " + (a | b));
        System.out.println("a ^ b = " + (a ^ b));
        System.out.println("~a = " + ~a);
        System.out.println("~b = " + ~b);
        System.out.println("a << 2 = " + (a << 2));
        System.out.println("b << 2 = " + (b << 2));
        System.out.println("a << 2 >> 2 = " + (a << 2 >> 2));
        System.out.println("b << 2 >> 2 = " + (b << 2 >> 2));
        System.out.println("a << 2 >>> 2 = " + (a << 2 >>> 2));
        System.out.println("b << 2 >>> 2 = " + (b << 2 >>> 2));
    }
}
