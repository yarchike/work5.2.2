package com.example.work522;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class FileUtil {
    static final String LOG_TAG = "myLogs";


    public static void writeFile(Context context, String datal, String datap) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(context.openFileOutput("login", MODE_PRIVATE)));) {

            bw.write(datal + "\n" + datap);
            bw.close();
            Log.d(LOG_TAG, "Файл записан");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public String readFile(Context context) {
        String line = "";
        String result = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput("login")));) {

            while ((line = br.readLine()) != null) {
                result = (result + line + ";");
                Log.d(LOG_TAG, line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String load(Context context) {
        String line = "";
        String result = "";
        if (isExternalStorageWritable()) {

            File file = new File(context.getApplicationContext().getExternalFilesDir(
                    null), "login.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(file));) {
                while ((line = br.readLine()) != null) {
                    result = (result + line + ";");
                    Log.d(LOG_TAG, line + "c sd");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void write(Context context, String datal, String datap) {
        if (isExternalStorageWritable()) {
            File file = new File(context.getExternalFilesDir(null), "login.txt");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(datal + "\n" + datap);
                bw.close();
                Log.d(LOG_TAG, "Файл записан на sd");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
