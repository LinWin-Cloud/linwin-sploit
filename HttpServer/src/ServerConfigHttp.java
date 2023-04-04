import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ServerConfigHttp
{
    public static String configRoot = "../config";

    public static String ReadCongfig(String configFile,String configContent)
    {
        try
        {
            File file = new File(configRoot+"/"+configFile);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line="";
            while ((line=bufferedReader.readLine())!=null)
            {
                if (line.indexOf(configContent) != -1){
                    line = line.substring(line.indexOf(configContent)+configContent.length(),line.lastIndexOf(";"));
                    //System.out.println(line+";");
                    break;
                }
            }
            bufferedReader.close();
            return line;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
    }
}
