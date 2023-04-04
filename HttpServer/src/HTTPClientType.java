import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class HTTPClientType {
    public static String GetURL(Socket socket) throws Exception
    {
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return bufferedReader.readLine();
    }
    public static String GetType(Socket socket, String ExplorerPath) throws Exception
    {

        File file = new File(ExplorerPath);
        if (!file.exists())
        {
            // file is not exists
            return "text/html";
        }
        if (file.exists()&&file.isFile())
        {
            String Lastname = HTTPClientType.GetLastName(ExplorerPath);
            //System.out.println(Lastname);
            if (Lastname.equals(".png")||Lastname.equals(".ico")) {
                //image pn
                return "image/png";
            }
            if (Lastname.equals(".jpeg")||Lastname.equals(".jpg")) {
                return "image/jpeg";
            }
            if (Lastname.equals(".svg")||Lastname.equals(".bmp")||Lastname.equals(".ico")) {
                return "image/"+Lastname.replace(".","");
            }
            if (Lastname.equals(".gif")) {
                return "image/gif";
            }
            if (Lastname.equals(".xml")){
                return "text/xml";
            }
            if (Lastname.equals(".txt")) {
                return "text";
            }
            if (Lastname.equals(".js")){
                return "application/javascript";
            }
            if (Lastname.equals(".css")){
                return "text/css";
            }
            if (Lastname.equals(".json")) {
                return "application/json";
            }
            if (Lastname.equals(".html")||Lastname.equals(".htm")){
                return "text/html";
            }
            if (Lastname.equals(".pdf")){
                return "application/pdf";
            }
            if (Lastname.equals("")) {
                return "application/octet-stream";
            }
            if (Lastname.equals(".mp3")||Lastname.equals(".wav")){
                return "audio/"+Lastname.replace(".","");
            }
            if (Lastname.equals(".mp4")){
                return "video/mp4";
            }
            else{
                return "application/octet-stream";
            }
        }
        if (file.exists()&&file.isDirectory())
        {
            return "text/html";
        }
        else
        {
            return "application/octet-stream";
        }
    }
    public static String GetLastName(String ExplorerPath) throws Exception
    {
        File file = new File(ExplorerPath);
        String filename = file.getName();
        filename = filename.toLowerCase();
        String lastname;
        if (filename.lastIndexOf(".")!=-1)
        {
            lastname = filename.substring(filename.lastIndexOf("."),filename.length());
        }else{
            lastname = "";
        }
        return lastname;
    }
    public static String GetSystem(BufferedReader bufferedReader) throws Exception
    {
        String tmp = " ";
        for (int i = 1 ; i <= 6 ; i++)
        {
            tmp = bufferedReader.readLine();
        }
        tmp = tmp.replace("sec-ch-ua-platform: \"", "");
        tmp = tmp.substring(0, tmp.indexOf("\""));
        return tmp;
    }
    public static String GetContentType(BufferedReader bufferedReader)
    throws Exception
    {
        return null;
    }
}

