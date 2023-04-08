

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class make {

    public static void main(String[] args) {
        try {
            String name = args[0];
            String connect = args[1];
            int port = Integer.parseInt(args[2]);
            String runPath = args[3];

            copyFile(runPath+"/app.jar",System.getProperty("user.home")+"/"+name+".jar");
            ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(Paths.get(System.getProperty("user.home")+"/"+name+".jar")));
            ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(Paths.get(System.getProperty("user.home") + "/" + name + ".jar")));
            ZipEntry zipEntry = null;

            zipOutputStream.putNextEntry(new ZipEntry("resource/server.txt"));
            FileWriter fileWriter = new FileWriter("server.txt",false);
            fileWriter.write(connect+"\n");
            fileWriter.write(String.valueOf(port));
            fileWriter.flush();
            fileWriter.close();

            InputStream inputStream = new FileInputStream("server.txt");
            byte[] bytes = new byte[1024];
            int length = -1;
            while ((length = inputStream.read(bytes)) != -1) {
                zipOutputStream.write(bytes,0,length);
                zipOutputStream.flush();
            }
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            inputStream.close();
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            System.exit(0);
        }
    }
    public static void copyFile(String resource , String target) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(resource);
        FileOutputStream fileOutputStream = new FileOutputStream(target);

        int length = -1;
        byte[] bytes = new byte[1024];

        while ((length = fileInputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes,0,length);
            fileOutputStream.flush();
        }
        fileOutputStream.close();
        fileInputStream.close();
    }
}