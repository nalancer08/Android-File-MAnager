package com.appbuilders.libraries;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Erick Sanchez on 26/01/2017.
 * CEO & CTO App Builders 
 * This class have the objective to be the central of files 
 */

public class FileManager {

    protected Context context;

    /**
    * Constructor
    * @param context: The application context
    */
    public FileManager(Context context) {

        this.context = context;
    }


    /**
    * Method to create root path
    */
    @Nullable
    public File makeRootPath() {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() );

        if(!path.exists()){
            path.mkdirs();
        }

        return path;
    }

    /**
    * Method to check if the root path exists
    */
    @NonNull
    public Boolean checkRootPath() {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() );

        return path.exists();
    }

    /**
    * Method to get the root path
    */
    @Nullable
    public File getRootPath() {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() );

        if ( path.exists() ) {
            return path;
        }

        return null;
    }

    /**
    * Mehtod to create a folder into the root path
    * @param folderName: The folder's name
    */
    @Nullable
    public File makeFolder(String folderName) {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() + "/" + folderName );

        if ( !path.exists() ) {
            path.mkdirs();
        }

        return path;
    }

    /**
    * Method to check if a folder exists
    * @param folderName: The folder's name
    */
    @Nullable
    public Boolean checkFolder(String folderName) {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() + "/" + folderName );

        return path.exists();
    }

    /**
    * Method to get a folder
    * @param folderName: The folder's name
    */
    @Nullable
    public File getFolder(String folderName) {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() + "/" + folderName );

        if ( path.exists() ) {
            return path;
        }
        return null;
    }

    /**
    * Method to create a file
    * @param folderName: Folder to create the file
    * @param fileName: Name of the file to be created
    */
    @Nullable
    public File makeFile( String folderName, String fileName ) {

        File path;

        if ( folderName.compareTo("") == 0 || folderName.equalsTo("root") ) {
            path = getRootPath(this.context);
        } else {
            path = this.getFolder(folderName);
        }

        return new File( path + "/" + fileName );
    }

    /**
    * Method to check a file
    * @param folderName: Folder to check the file
    * @param fileNmae: Name of the fiel to be checked
    */
    @Nullable
    public Boolean checkFile( String folderName, String fileName ) {

        File path;

        if ( folderName.compareTo("") == 0 || folderName.equalsTo("root") ) {
            path = getRootPath(context);
        } else {
            path = this.getFolder(folderName);
        }

        File f = new File( path + "/" + fileName );
        return f.exists();
    }

    /**
    * Method to get a file
    * @param folderName: Folder to check the file
    * @param fileNmae: Name of the fiel to be checked
    */
    @Nullable
    public File getFile( String folderName, String fileName ) {

        File path;

        if ( folderName.compareTo("") == 0 || folderName.equalsTo("root") ) {
            path = getRootPath(context);
        } else {
            path = this.getFolder(folderName);
        }

        File file = new File( path + "/" + fileName );
        return file;
    }

    /**
    * Method to get a file wirter
    * @param folderName: Folder to check the file
    * @param fileNmae: Name of the fiel to be checked
    */
    @Nullable
    public FileWriter getFileWriter( String folderName, String fileName ) {

        File path;

        if ( folderName.compareTo("") == 0 || folderName.equalsTo("root") ) {
            path = getRootPath(context);
        } else {
            path = this.getFolder(folderName);
        }

        FileWriter file = new FileWriter( path + "/" + fileName );
        return file;
    }

    /**
    * Method to list external storage
    */
    @Nullable
    public void listExternalStorage() {

        String path = Environment.getExternalStorageDirectory().getPath();
        File f = new File(path);

        File[] files = f.listFiles();

        for (File inFile : files) {
            if (inFile.isDirectory()) {
                // is directory
                Log.d("AB_DEV", "Is directory: " + inFile.getName());

                File f2 = new File(inFile.getPath());
                File[] filesSub = f2.listFiles();
                for (File inFile2 : filesSub) {
                    Log.d("AB_DEV", "--" + inFile2.getName());
                    if ( inFile2.getName().equals("hummingbird.zip") ) {
                        Log.d("AB_DEV", "----> " + inFile2.getPath());
                        ///storage/emulated/0/media/hummingbird.zip
                    }
                }

            } else {
                Log.d("AB_DEV", "Is file: " + inFile.getName());
            }
        }
    }
}
