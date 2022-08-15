package book;

import java.io.*;
import java.nio.file.Paths;

/******************************
 * 用途说明: @Description 文件输入与输出
 * 作者姓名: @author Administrator
 * 创建时间: @date 2022-08-15 14:14
 ******************************/
public class FileInputAndOutput {
    private static String content = "";
    private static File file = new File(String.valueOf(Paths.get(System.getProperty("user.dir") + "\\aa.txt")));

    public static void main(String[] args) {
        //if file doesnt exists, then create it
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        fileWrite();
        fileAddString();
        fileRead();
    }

    private static void fileWrite() {
        System.out.println("The function fileWrite is running to write file");
        try {
            FileOutputStream fop = new FileOutputStream(file);
            content = "Hello wds, the file is " + file
                    + "\nThis is the text content";
            // get the content in bytes
            byte[] contentInBytes = content.getBytes();
            fop.write(contentInBytes);
            fop.flush();
            fop.close();
            System.out.println("The function fileWrite Done！\n");
            fop.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fileAddString() {
        System.out.println("The function fileAddString is running to add string to file");
        content = "\nThis is the content to write into file " + file.getName()
                + "\nThis content is appended to the end of the file " + file.getName();
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
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
        try (FileReader fileReader = new FileReader(file); BufferedReader br = new BufferedReader(fileReader)) {
            content = br.readLine();
            while (content != null) {
                System.out.println(content);
                content = br.readLine();
            }
            System.out.println();
            System.out.print("The function fileRead Done！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
