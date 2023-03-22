
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SocketServer {
    public static boolean connect = false;
    public static String sendCommand = "none";
    public static void main(String[] args) {
        Thread options = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.print("LinwinSploit (web/attack/trojan_virus) $ ");
                    Scanner scanner = new Scanner(System.in);
                    String command = scanner.nextLine();
                }
            }
        });
        options.start();

        try {
            ExecutorService executorService = Executors.newFixedThreadPool(100);
            int port = Integer.parseInt(args[0]);
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                Socket socket = serverSocket.accept();
                Future<Integer> future = executorService.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        SocketServer.connect = true;
                        OutputStream outputStream = socket.getOutputStream();
                        InputStream inputStream = socket.getInputStream();

                        PrintWriter printWriter = new PrintWriter(outputStream);
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        while (true) {
                            String message = bufferedReader.readLine();
                            if (message == null) {
                                break;
                            }
                            
                        }
                        bufferedReader.close();
                        socket.shutdownInput();
                        socket.shutdownOutput();
                        socket.close();
                        SocketServer.sendCommand = "none";

                        return 0;
                    }
                });
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("ERROR"+exception.getMessage());
            System.exit(1);
        }
    }
}
