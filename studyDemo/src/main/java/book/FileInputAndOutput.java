package book;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/******************************
 * 用途说明: @Description 文件输入与输出
 * 作者姓名: @author Administrator
 * 创建时间: @date 2022-08-15 14:14
 ******************************/
public class FileInputAndOutput {
    private static String content = "";
    private static File file = new File(String.valueOf(Paths.get(System.getProperty("user.dir"))));
    private static String fileName = "\\aa.txt";
    private static File filePath = new File(file + fileName);

    public static void main(String[] args) {
        //if file doesnt exists, then create it
        if (!filePath.exists()) {
            try {
                filePath.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        fileWrite();
        fileAddString();
        fileRead();
        fileCopy();
        fileCopy1();
    }

    private static void fileWrite() {
        System.out.println("The function fileWrite is running to write file");
        try {
            FileOutputStream fop = new FileOutputStream(filePath);
            content = "Hello wds, the file is " + filePath
                    + "\nThis is the text content";
            // get the content in bytes
            byte[] contentInBytes = content.getBytes();
            fop.write(contentInBytes);
            fop.flush();
            fop.close();
            System.out.println("The function fileWrite Done！\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fileAddString() {
        System.out.println("The function fileAddString is running to add string to file");
        content = "\nThis is the content to write into file " + filePath.getName()
                + "\nThis content is appended to the end of the file " + filePath.getName();
        try {
            FileWriter fw = new FileWriter(filePath.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            System.out.println("The function fileAddString Done！\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fileRead() {
        System.out.println("The function fileRead is running to read file，the content is：\n");
        try (FileReader fileReader = new FileReader(filePath); BufferedReader br = new BufferedReader(fileReader)) {
            content = br.readLine();
            while (content != null) {
                System.out.println(content);
                content = br.readLine();
            }
            System.out.println();
            System.out.println("The function fileRead Done！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fileCopy() {
        System.out.println("The function fileCopy is running to read file，the content is：\n");
        try {
            //读取文件(字节流)
            FileInputStream in = new FileInputStream(filePath);
            //写入相应的文件
            FileOutputStream out = new FileOutputStream(file + "\\bb.txt");
            //读取数据
            //一次性取多少字节
            byte[] bytes = new byte[2048];
            //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
            int n;
            //循环取出数据
            while ((n = in.read(bytes, 0, bytes.length)) != -1) {
                //转换成字符串
                String str = new String(bytes, 0, n, StandardCharsets.UTF_8); //这里可以实现字节到字符串的转换，比较实用
                System.out.println(str);
                //写入相关文件
                out.write(bytes, 0, n);
                //清除缓存向文件写入数据
                out.flush();
            }
            //关闭流
            in.close();
            out.close();
            System.out.println();
            System.out.println("The function fileCopy Done！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fileCopy1() {
        System.out.println("The function fileCopy1 is running to read file，the content is：\n");
        try {
            //读取文件(缓存字节流)
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));
            //写入相应的文件
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file + "\\cc.txt"));
            //读取数据
            //一次性取多少字节
            byte[] bytes = new byte[2048];
            //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
            int n;
            //循环取出数据
            while ((n = in.read(bytes, 0, bytes.length)) != -1) {
                //转换成字符串
                String str = new String(bytes, 0, n, StandardCharsets.UTF_8);
                System.out.println(str);
                //写入相关文件
                out.write(bytes, 0, n);
                //清除缓存，向文件写入数据
                out.flush();
            }
            //关闭流
            in.close();
            out.close();
            System.out.println();
            System.out.print("The function fileCopy1 Done！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}