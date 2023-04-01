import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Control {
    public static String terminal = "LinwinSploit> ";
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                Socket socket = serverSocket.accept();

                try {
                    InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    PrintWriter printWriter = new PrintWriter(outputStream);

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                while (true) {
                                    Scanner scanner = new Scanner(System.in);
                                    System.out.print(Control.terminal);
                                    String input = scanner.nextLine();
                                    printWriter.println(input);
                                    printWriter.flush();
                                }
                            }catch (Exception exception){
                                exception.printStackTrace();
                            }
                        }
                    });
                    thread.start();

                    while (true) {
                        String message = bufferedReader.readLine();
                        if (message == null) {
                            System.exit(0);
                            break;
                        }
                        System.out.println(message);
                        System.out.println();
                        System.out.print(terminal);
                    }
                    socket.close();
                }
                catch (Exception exception){
                    try{
                        socket.close();
                    }catch (Exception exception1){
                        exception1.printStackTrace();
                    }
                }
            }
        }
        catch (Exception exception){
            System.out.println("START SERVICE ERROR");
            System.exit(0);
        }
    }
}