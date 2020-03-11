package com.example.harihara_medicals.utils;

import android.os.Environment;

import com.example.harihara_medicals.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

//this class will manage all create file name
//and it contains all folder paths for all types (image,video etc..)
public class DirManager {
    private static final String EXTENSION_JPG = ".jpg";
    private static final String APP_FOLDER_NAME = MyApp.context().getString(R.string.app_folder_name);


    //Main App Folder: /sdcard/HariHaraMedicals/
    public static String mainAppFolder() {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + APP_FOLDER_NAME + "/");
        //if the directory is not exists create it
        if (!file.exists())
            file.mkdir();


        return file.getAbsolutePath();
    }

    //get stored profile photos directory : /sdcard/HariHaraMedicals/ProfilePhotos
    public static String getUserProfilePhotoFolder() {
        File file = new File(mainAppFolder() + "/" + APP_FOLDER_NAME + " " + "Profile Photos");
        if (!file.exists()) {
            file.mkdirs();
        }
        createNoMediaFile(file);

        return file.getAbsolutePath();
    }



    //the user's image file when he wants to change his photo
    public static File getMyPhotoPath() {
        File file = new File(mainAppFolder(), "user-img.jpg");
        //delete old file
        file.delete();
        return file;
    }

    //this will generate a new file name it will generate the date as year month day Millisecond
    //more info https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
    // the final output would be like this IMG-201804301230.jpg
    public static String generateNewName() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddSSSS", Locale.US); //the Locale us is to use english numbers
        return "IMG" + "-" + sdf.format(date);
    }

    //create the file to save the user profile into it :/sdcard/HariHaraMedicals/ProfilePhotos/IMG-"DATE".jpg
    public static File generateUserProfileImage() {
        return new File(getUserProfilePhotoFolder() + "/" + generateNewName() + EXTENSION_JPG);
    }
    //this will prevent the gallery or audio player app in the device from showing "sent" files (images,videos,audio,voice messages) by our app
    //basically just hide them
    public static void createNoMediaFile(File folderPath) {
        File file = new File(folderPath + "/" + ".nomedia");
        try {
            if (!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getDatabasesFolder() {
        File file = new File(mainAppFolder() + "/" + "Backups/Databases");

        if (!file.exists())
            file.mkdirs();

        return file;


    }



}
