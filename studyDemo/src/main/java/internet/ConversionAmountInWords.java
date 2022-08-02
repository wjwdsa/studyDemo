package main.java.internet;

import java.text.DecimalFormat;
import java.util.Scanner;

/******************************
 * 用途说明: @Description 转换金额为大写金额
 * 作者姓名: @author Administrator
 * 创建时间: @date 2022-07-31 16:48
 ******************************/
public class ConversionAmountInWords {
    private static String[] numBig = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static String[] numFloat = {"厘", "分", "角"};// 小数单位
    private static String[] numInt = {"", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟"};// 整数单位

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入金额：");
        double money = input.nextDouble();
        //格式化double数字
        DecimalFormat df = new DecimalFormat("#0.###");//此时strNum小数位最多3位
        String strNum = df.format(money);
        if (strNum.indexOf(".") > 0) {//判断是否有小数
            String strMoneyInt = strNum.substring(0, strNum.indexOf("."));//截取小数点前面的数字
            String strMoneyFloat = strNum.substring(strNum.indexOf(".") + 1);//截取小数点后面的数字
            if (strMoneyInt.length() > 12) {
                System.out.println("数字太大了，转换不了");
            } else {
                System.out.println(getInt(strMoneyInt) + "元" + getDouble(strMoneyFloat));
            }
        } else {
            if (strNum.length() > 12) {
                System.out.println("数字太大了，转换不了");
            } else {
                System.out.println(getInt(strNum) + "元整");
            }
        }
    }

    /**
     * 整数部分
     */
    private static String getInt(String str) {
        str = addUnit(str, "before");
        //替换字符串多余的字符
        while (str.contains("零拾")) {
            str = str.replace("零拾", "零");
        }
        while (str.contains("零佰")) {
            str = str.replace("零佰", "零");
        }
        while (str.contains("零仟")) {
            str = str.replace("零仟", "零");
        }
        while (str.contains("零万")) {
            str = str.replace("零万", "万");
        }
        while (str.contains("零亿")) {
            str = str.replace("零亿", "亿");
        }
        while (str.contains("亿万")) {
            str = str.replace("亿万", "亿");
        }
        while (str.contains("零零")) {
            str = str.replace("零零", "");
        }
        return removeEndOfZero(str);
    }

    /**
     * 小数部分
     */
    private static String getDouble(String str) {
        //解决单位错位
        if (str.length() == 1) {
            str = str + "00";
        } else if (str.length() == 2) {
            str = str + "0";
        }

        str = addUnit(str, "behind");
        //替换字符串多于的字符
        while (str.contains("零角")) {
            str = str.replace("零角", "");
        }
        while (str.contains("零分")) {
            str = str.replace("零分", "");
        }
        while (str.contains("零厘")) {
            str = str.replace("零厘", "");
        }
        while (str.contains("零零")) {
            str = str.replace("零零", "");
        }

        return removeEndOfZero(str);
    }

    /**
     * 添加单位
     */
    private static String addUnit(String str, String beforeOrBehind) {
        str = new StringBuffer(str).reverse().toString();//反转字符串
        StringBuilder strB = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {//把单位添加进去
            if (beforeOrBehind.equals("before")) {
                strB.append(numInt[i]);
            } else if (beforeOrBehind.equals("behind")) {
                strB.append(numFloat[i]);
            }
            strB.append(numBig[str.charAt(i) - 48]);
        }
        str = strB.reverse().toString();//把反转过的字符串还原
        return str;
    }

    /**
     * 去除零的结尾
     */
    private static String removeEndOfZero(String str) {
        if (str.lastIndexOf("零") == str.length() - 1) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
}
