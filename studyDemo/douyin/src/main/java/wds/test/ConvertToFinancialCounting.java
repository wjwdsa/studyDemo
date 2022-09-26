package wds.test;

import java.text.NumberFormat;
import java.util.Scanner;

/******************************
 * 用途说明: @Description 将金额转换为财务计数法，即将10000000000转换为10，,00,000,000
 * 作者姓名: @author Administrator
 * 创建时间: @date 2022-07-31 15:46
 ******************************/
public class ConvertToFinancialCounting {

    public static void main(String[] args) {
        //扫描仪
        Scanner input = new Scanner(System.in);
        //询问用户输入数字
        System.out.println("请输入一串数字：");
        //接收用户输入的数据
        String cash = input.next();
        System.out.println("第一种方法转换的结果为：" + whileLoopMethod(cash));
        System.out.println("第二种方法转换的结果为：" + RegularExpressionMethod(cash));
        System.out.println("第三种方法转换的结果为：" + numberFormatMethod(cash));
        System.out.println("第四种方法转换的结果为：" + forLoopMethod(cash));
    }

    /**
     * 第一种方法，通过循环从后向前截取长度并加上逗号
     */
    private static String whileLoopMethod(String cash) {
        StringBuilder ss = new StringBuilder();
        while (cash.length() > 3) {
            ss.insert(0, "," + cash.substring(cash.length() - 3));
            cash = cash.substring(0, cash.length() - 3);
        }
        ss.insert(0, cash);
        return ss.toString();
    }

    /**
     * 第二种方法，通过正则表达式转换
     * 正则前瞻     零宽断言
     * \d代表字符串，Java需要转译，变为\\d
     * $代表整个字符串
     * ?= 代表断言，就是问一下是否满足当前的匹配规则
     * 为了避免最前面是3个数字，最前面出现逗号的问题，需要去掉边界，用？=\\B，表示当到达边界的时候不再匹配规则
     */
    private static String RegularExpressionMethod(String cash) {
        return cash.replaceAll("(?=\\B(\\d{3})+$)", ",");
    }

    /**
     * 第三种方法，通过NumberFormat方法
     */
    private static String numberFormatMethod(String cash) {
        return NumberFormat.getInstance().format(Long.parseLong(cash));
    }

    /**
     * 第四种方法，通过for循环方法
     */
    private static String forLoopMethod(String cash) {
        StringBuilder sb = new StringBuilder(cash);
        for (int i = cash.length(); i > 0; ) {
            i -= 3;
            if (i <= 0) {
                break;
            }
            sb.insert(i, ",");
        }
        return sb.toString();
    }
}
