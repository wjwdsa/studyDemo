package book.BeatBox;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/******************************
 * @Description BeatBox服务器端最终版
 * @author Administrator
 * @date 2022-09-03 12:40
 ******************************/
public class MusicServer {
    private ArrayList<ObjectOutputStream> clientOutputStream;

    public static void main(String[] args) {
        new MusicServer().go();
    }

    private void go() {
        clientOutputStream = new ArrayList<>();
        try {
            ServerSocket serverSock = new ServerSocket(4242);
            while (true) {
                Socket clientSocket = serverSock.accept();
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                clientOutputStream.add(out);

                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                System.out.println("got a connecttion");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tellEveryone(Object one, Object two) {
        for (ObjectOutputStream objectOutputStream : clientOutputStream) {
            try {
                objectOutputStream.writeObject(one);
                objectOutputStream.writeObject(two);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class ClientHandler implements Runnable {
        Socket clientSocket;
        ObjectInputStream in;

        ClientHandler(Socket socket) {
            try {
                clientSocket = socket;
                in = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            Object o1;
            Object o2;
            try {
                while ((o1 = in.readObject()) != null) {
                    o2 = in.readObject();
                    System.out.println("read two objects");
                    tellEveryone(o1, o2);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
