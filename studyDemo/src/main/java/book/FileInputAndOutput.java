package book;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;

/******************************
 * 用途说明: @Description 文件输入与输出
 * 作者姓名: @author Administrator
 * 创建时间: @date 2022-08-15 14:14
 ******************************/
public class FileInputAndOutput {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File fileSource = new File(String.valueOf(Paths.get(System.getProperty("user.dir"))));
        String filePath = fileSource + "\\aa.txt";
        String fileEncoding = String.valueOf(StandardCharsets.UTF_8);
        FileWriter out = new FileWriter(filePath);
        String str = "Hello wds" + "\n" + "fileName =" + "\n" + "aa.txt";
        out.write(str);
        out.close();
        Scanner in = new Scanner(Paths.get(filePath), fileEncoding);
        while (in.hasNext()) {
            String temp = in.nextLine();
            System.out.println("temp = " + temp);
        }
        //InputStream与OutputStream 输入与输出串流
        InputStream input = new FileInputStream(filePath);
        byte[] b = new byte[1024];
        input.read(b);
        input.close();
        OutputStream output = new FileOutputStream(filePath);
        b = str.getBytes();
        output.write(b);
        output.close();
    }
}
