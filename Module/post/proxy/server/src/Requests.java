import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class Requests {

    private URL url;
    private int code;
    private Socket socket;
    private PrintWriter printWriter;
    private String httpMethod = "GET";
    private OutputStream outputStream;

    public void RequestsUrl(String url)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {
            this.url = new URL(url);
            httpURLConnection = (HttpURLConnection) this.url.openConnection();

            httpURLConnection.setRequestMethod(this.httpMethod);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:108.0) Gecko/20100101 Firefox/108.0");

            httpURLConnection.connect();

            int status_code = httpURLConnection.getResponseCode();
            String contentType = httpURLConnection.getContentType();

            this.printWriter.println("HTTP/1.1 "+status_code+" OK");
            this.printWriter.println("Content-Type: "+contentType);
            this.printWriter.println("Server: openLinwin/"+ProxyService.version);
            this.printWriter.println();
            this.printWriter.flush();

            InputStream inputStream = httpURLConnection.getInputStream();
            OutputStream outputStream = this.outputStream;

            byte[] bytes = new byte[1024];
            int length = 0;

            while ((length = inputStream.read(bytes)) != -1)
            {
                outputStream.write(bytes,0,length);
            }
            outputStream.flush();

            outputStream.close();
            this.printWriter.close();
            httpURLConnection.disconnect();
            this.socket.close();
        }
        catch (Exception exception)
        {
            try
            {
                this.printWriter.println("<h1>500 Server Error</h1>");
                this.printWriter.println("Error Message: ");
                this.printWriter.println(exception.getLocalizedMessage());
                this.printWriter.println("<div style='width=100%;height:2px;background-color:black'></div>");
                this.printWriter.println("openLinwin/"+ProxyService.version);
                this.printWriter.flush();

                httpURLConnection.disconnect();
                this.socket.close();
            }
            catch (Exception exception1)
            {
                exception.printStackTrace();
            }
        }
    }
    public void Setting(Socket socket,PrintWriter printWriter,OutputStream outputStream)
    {
        this.socket = socket;
        this.printWriter = printWriter;
        this.outputStream = outputStream;

    }
    public void setMethod(String method)
    {
        // set the http method , that the proxy service will send to the target server.
        this.httpMethod = method;
    }
}
