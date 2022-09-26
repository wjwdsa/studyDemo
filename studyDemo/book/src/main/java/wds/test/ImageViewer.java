package wds.test;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/******************************
 * 用途说明: @Description 创建一个图片查看器
 * 作者姓名: @author Administrator
 * 创建时间: @date 2022-07-31 15:27
 ******************************/
public class ImageViewer {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new ImageViewerFrame();
            frame.setTitle("wds.test.ImageViewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    static class ImageViewerFrame extends JFrame {
        private static final int DEFAULT_HIGHT = 1800;
        private static final int DEFAULT_WIDTH = 900;
        private JFileChooser chooser;
        private JLabel label;

        ImageViewerFrame() {
            setSize(DEFAULT_WIDTH, DEFAULT_HIGHT);
            label = new JLabel();
            add(label);
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            JMenuBar menuBar = new JMenuBar();
            setJMenuBar(menuBar);
            JMenu menu = new JMenu("File");
            menuBar.add(menu);
            JMenuItem openItem = new JMenuItem("open");
            menu.add(openItem);
            openItem.addActionListener(event -> {
                int result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String name = chooser.getSelectedFile().getPath();
                    label.setIcon(new ImageIcon(name));
                }
            });
            JMenuItem exitItem = new JMenuItem("Exit");
            menu.add(exitItem);
            exitItem.addActionListener(event -> System.exit(0));
        }
    }
}
