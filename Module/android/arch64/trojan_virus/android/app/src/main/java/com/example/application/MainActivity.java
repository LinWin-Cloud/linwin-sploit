package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
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
            textView.setText(host+" "+port);

            Socket socket = new Socket(host,port);
            OutputStream outputStream = socket.getOutputStream();
            InputStream SocketInputStream = socket.getInputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);

            BufferedReader buf = new BufferedReader(new InputStreamReader(SocketInputStream));

            printWriter.println("Connect="+ Settings.Secure.getString(getContentResolver(),"bluetooth_name"));
            printWriter.flush();

            while (true)
            {
                String message = buf.readLine();
                if (message == null) {
                    System.exit(0);
                }
                try {
                }
                catch (Exception exception){
                    socket.close();
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            TextView textView = (TextView) findViewById(R.id.textview);
            textView.setText(exception.getMessage());
        }
    }
}