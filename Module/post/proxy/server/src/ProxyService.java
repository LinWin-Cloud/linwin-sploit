
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxyService {
    private static int BootNum = 0;

    public static ProxyService proxyService = new ProxyService();
    public static String ProxyUrl = "";
    public static int ProxyPort = 0;
    public static String version = "";

    public static void main(String[] args)
    {
        ProxyService.ProxyUrl = args[0];
        ProxyService.ProxyPort = Integer.parseInt(args[1]);
        ProxyService.version = "1.4";
        try
        {
            if (proxyService.bootServerSocket())
            {
                ServerSocket serverSocket = new ServerSocket(ProxyService.ProxyPort);
                for (int i=0 ;i<16 ;i++)
                {
                    Thread thread = new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            while (true)
                            {
                                try
                                {
                                    Socket socket = serverSocket.accept();
                                    proxyService.ProxyServer(socket);
                                }
                                catch (Exception exception)
                                {
                                    exception.printStackTrace();
                                }
                            }
                        }
                    });
                    thread.start();
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public Boolean bootServerSocket() throws InterruptedException {
        while (true)
        {
            try
            {
                ProxyService.BootNum = ProxyService.BootNum + 1;
                System.out.println("[Info] Start Port ["+ProxyService.BootNum+"]: "+ProxyService.ProxyPort);
                ServerSocket serverSocket = new ServerSocket(ProxyService.ProxyPort);
                serverSocket.close();
                break;
            }
            catch (Exception exception)
            {
                Thread.sleep(200);
                System.gc();
                continue;
            }
        }
        return true;
    }
    public void ProxyServer(Socket socket)
    {
        try {
            /**
             * All the value must get.
             * From the socket TCP requests.
             */
            OutputStream outputStream = null;
            InputStream inputStream = null;
            PrintWriter printWriter = null;
            BufferedReader bufferedReader = null;

            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();

            printWriter = new PrintWriter(outputStream);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            /**
             * get the Really string text of the url , and deal with the Http Proxy
             */
            String requests = bufferedReader.readLine();

            String getHttpMethod = requests.substring(0,requests.indexOf(" "));
            String getUrl = requests.substring(requests.indexOf(" ")+1,requests.lastIndexOf("HTTP/")-1);

            String ProxyUrl = ProxyService.ProxyUrl + getUrl;


            Requests requestsDom = new Requests();
            requestsDom.Setting(socket,printWriter,outputStream);
            requestsDom.setMethod(getHttpMethod);

            requestsDom.RequestsUrl(ProxyUrl);
        }
        catch (Exception exception) {}
    }
}
