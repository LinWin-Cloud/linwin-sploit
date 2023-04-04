import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server {
    public static String SeverDir = System.getProperty("user.dir");
    public static String[] DefaultPage = {"index.html","index.htm"};

    public static void main(String[]args) throws Exception
    {
        if (args.length == 0)
        {
            System.out.println("[ERR] Must Have A Value to Server.");
            System.exit(0);
        }
        String ServerPort = args[0];
        int port = 0;
        try{
            port = Integer.valueOf(ServerPort);
        }catch(Exception exception)
        {
            System.out.println("[ERR] Is Not A int Number: "+ServerPort);
            System.exit(0);
        }
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("[Info] Start HTTP Server On Port: "+ServerPort+" .Visit Url: http://127.0.0.1:"+ServerPort);

        while (true){
            Socket socket = serverSocket.accept();

            ExecutorService executorService = Executors.newFixedThreadPool(1);
            Future<Integer> future = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Server.ServerHTTP(socket);
                    return 0;
                }
            });
            executorService.shutdown();
        }
    }
    public static void ServerHTTP(Socket socket)
    {
        try
        {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String HTTPURL = bufferedReader.readLine();

            if (HTTPURL == null)
            {
                socket.close();
            }
            else
            {
                HTTPURL = java.net.URLDecoder.decode(HTTPURL,"UTF-8");
                String DoWithURL = HTTPURL.substring(HTTPURL.indexOf(" ")+1,HTTPURL.lastIndexOf("HTTP/")-1);

                System.out.println("[ HTTP "+socket.getInetAddress()+" ] "+HTTPURL);

                Send_Dir_And_File_To_Client(printWriter, socket, DoWithURL, outputStream);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
    public static void Send_Dir_And_File_To_Client(PrintWriter pWriter,Socket socket,String HTTPURL,OutputStream outputStream)
    {
        /**
         * If the url content is '/' return a Enter road Error.
         */

        File file = new File(SeverDir+HTTPURL);
        /**
         * If the path is a fil then run this function
         * send the file to the web client.
         */
        
        if (file.isFile()&&file.exists())
        {
            try
            {
                pWriter.println("HTTP/1.1 200 OK");
                pWriter.println("Content-Type:"+HTTPClientType.GetType(socket, SeverDir+HTTPURL));
                pWriter.println();
                pWriter.flush();

                byte[] bytes = new byte[1024];
                FileInputStream fileInputStream = new FileInputStream(SeverDir+HTTPURL);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                int length = 0;
                while((length = fileInputStream.read(bytes)) != -1)
                {
                    bufferedOutputStream.write(bytes,0,length);
                    bufferedOutputStream.flush();
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                fileInputStream.close();
                socket.close();
                //socket.shutdownInput();
            }
            catch (Exception exception)
            {
                //exception.printStackTrace();
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        /**
         * the path is dir and send all the files in the dir to the web client.
         * LinWin Http Server
         */
        if (file.exists()&&file.isDirectory())
        {
            try
            {
                pWriter.println("HTTP/1.1 200 OK");
                pWriter.println("Content-Type:"+HTTPClientType.GetType(socket, HTTPURL));
                pWriter.println();
                pWriter.flush();

                /**
                 * if the default page and send it to the web client.
                 */
                File[] files = file.listFiles();

                for (int i =0;i < files.length ;i++)
                {
                    if (files[i].isFile())
                    {
                        for (int a=0;a<DefaultPage.length;a++)
                        {
                            if (files[i].getName().equals(DefaultPage[a]))
                            {
                                byte[] bytes = new byte[1024];
                                FileInputStream fileInputStream = new FileInputStream(SeverDir+HTTPURL+"/"+files[i].getName());
                                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                                int length = 0;

                                while((length = fileInputStream.read(bytes)) != -1)
                                {
                                    bufferedOutputStream.write(bytes,0,length);
                                    bufferedOutputStream.flush();
                                }

                                bufferedOutputStream.flush();
                                bufferedOutputStream.close();
                                fileInputStream.close();
                                socket.close();
                                break;
                            }
                        }
                    }
                }
                /**
                 * send the Dir Content
                 */
                pWriter.println("<meta charset='utf-8'/>");
                pWriter.println("<h2>IndexOf: "+HTTPURL+"</h2>");
                pWriter.println("Last Dir: <a href='../'>../</a><br />");
                pWriter.println("<div style='width:98%;height:2px;background-color:black'></div><br />");
                pWriter.flush();

                for (int i =0;i < files.length ;i++)
                {
                    if (files[i].isDirectory())
                    {
                        pWriter.println("<a href='"+files[i].getName()+"/"+"'> * Directory: "+files[i].getName()+"</a><br />");
                        pWriter.flush();
                    }
                }
                for (int i =0;i < files.length ;i++)
                {
                    if (files[i].isFile())
                    {
                        pWriter.println("<a href='"+files[i].getName()+"'> * File: "+files[i].getName()+"</a><br />");
                        pWriter.flush();
                    }
                }
                pWriter.println("<br /><div style='width:98%;height:2px;background-color:black'></div>");
                pWriter.flush();
                socket.close();
                //socket.shutdownOutput();
            }
            catch(Exception exception)
            {
                //exception.printStackTrace();
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        if (!file.exists())
        {
            pWriter.println("HTTP/1.1 404 Not Find");
            pWriter.println("Content-Type:text/html");
            pWriter.println("");
            pWriter.flush();
            pWriter.println("<h2>404 Not Find</h2>");
            pWriter.flush();
            try {
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

