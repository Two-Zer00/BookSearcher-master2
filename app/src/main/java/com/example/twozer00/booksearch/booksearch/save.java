package com.example.twozer00.booksearch.booksearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class save {

    private Context TheThis;
    public static String NameOfFolder = "/Movie Searcher";
    private String NameOfFile = "imagen-";

    public void SaveImage(Context context, Bitmap ImageToSave) {

        TheThis = context;
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder;
        //String name=Environment.getRootDirectory()
        String CurrentDateAndTime = getCurrentDateAndTime();
        File dir = new File(file_path);

        if (!dir.exists()) {
            dir.mkdirs();
        }
            File file = new File(dir, NameOfFile + CurrentDateAndTime + ".png");

            try {
                FileOutputStream fOut = new FileOutputStream(file);
                ImageToSave.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                fOut.flush();
                fOut.close();

                MakeSureFileWasCreatedThenMakeAvabile(file);
                AbleToSave();

            } catch (FileNotFoundException e) {
                UnableToSave(e.getMessage());

            } catch (IOException e) {
                UnableToSave(e.getMessage());
            }

    }

    private void MakeSureFileWasCreatedThenMakeAvabile(File file){
        MediaScannerConnection.scanFile(TheThis,
                new String[] { file.toString() } , null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }

    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    private void UnableToSave(String e) {
        Toast.makeText(TheThis, "Can't save the image." + e, Toast.LENGTH_SHORT).show();
    }

    private void AbleToSave() {
        Toast.makeText(TheThis, "Image saved.", Toast.LENGTH_SHORT).show();
    }
    public Uri ShareContent(Context context,Bitmap Image){

        TheThis = context;
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder;
        //String name=Environment.getRootDirectory()
        String CurrentDateAndTime = getCurrentDateAndTime();
        File dir = new File(file_path);
        Uri uri=null;

        if (!dir.exists()) {
            dir.mkdirs();
        }
            File file = new File(dir, NameOfFile + CurrentDateAndTime + ".png");

            try {
                FileOutputStream fOut = new FileOutputStream(file);
                Image.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                fOut.flush();
                fOut.close();
                uri= Uri.fromFile(file);

            } catch (FileNotFoundException e) {
                UnableToSave(e.getMessage());

            } catch (IOException e) {
                UnableToSave(e.getMessage());
            }
            return uri;
        }

    }
