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
 * Created by Erick Sanchez on 28/10/2016.
 * CEO & CTO App Builders 
 * This class have the objective to be the central of files
 */

public class DataManager extends FileManager, Crypher, JsonManager {

	/**
    * Method to save data into a file
    * @param folderName: Folder to take the file
    * @prama fileName: Name of the file to append the content
    * @param content: The content to be write o append
    */    
    public void saveData(String folderName, String fileName, String content) {
        
        try {

            FileWriter file = this.getFileWriter(folderName, fileName);
            file.write(content);
            file.flush();
            file.close();
        } catch (IOException e) {
            Log.e("AB_DEV", "Error in writing: " + e.getLocalizedMessage());
        }
    }

    /**
	* This method use simpleEncryption of Crypher, and then save data in the file
	* @param folderName: Folder to take the file
    * @prama fileName: Name of the file to append the content
    * @param content: The content to be write o append
    */
    public void saveSecureData(String folderName, String fileName, String content) {

    	try {

    		Crypher crypher = new Crypher();
    		KeyChain key = crypher.256Key(this.context);
    		FileWriter file = this.getFileWriter(folderName, fileName);
    		file.write(crypher.simpleEncryption(key, content));
            file.flush();
            file.close();

    	} catch (IOException e) {
    		Log.e("AB_DEV", "Error in writing: " + e.getLocalizedMessage());
    	}
    }

    /**
    * Method to get saved raw data in a file
    * @param folderName: Folder to take the file
    * @prama fileName: Name of the file to read the content
    */
    @Nullable
    public String getData(String folderName, String fileName) {
        
        try {

            File f = this.getFile(folderName, fileName);
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);

        } catch (IOException e) {
            Log.e("EB_DEV", "Error in reading: " + e.getLocalizedMessage());
        }

        return null;
    }

    /**
	* This method get saved encrypt data, and then uses simpleDecryption of Crypher
	* @param folderName: Folder to take the file
    * @prama fileName: Name of the file to read the content
    */
    public String getSecureData(String folderName, String fileName) {

    	try {

            File f = this.getFile(folderName, fileName);
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            Crypher crypher = new Crypher();
    		KeyChain key = crypher.256Key(this.context);
            return new String(crypher.simpleDecryption(key, buffer));

        } catch (IOException e) {
            Log.e("EB_DEV", "Error in reading: " + e.getLocalizedMessage());
        }

        return null;
    }

    /**
    * Method to get data from assets folder
    * @param fileName: Name of the fiel into the folder
    * @param codification: Type of codification for file
    */
    @Nullable
    public String getDataFromAssets(String fileName, String codification) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, codification);
        } catch (IOException e) {
            Log.e("DXGO", "Error in Reading: " + e.getLocalizedMessage());
            return null;
        }
    }

    /**
    * Method to get data from assets folder with default codification
    * @param fileName: Name of the fiel into the folder
    */
    @Nullable
    public String getDataFromAssets(String fileName) {

        return this.getDataFromAssets(fileName, "UTF-8");
    }

    /**
    * Method to get data from assets folder with default codification
    * @param fileName: Name of the fiel into the folder
    */
    @Nullable
    public String getDataFromAssets2(String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
                String jsonString = writer.toString();
                return jsonString;
            } finally {
                is.close();
            }
        } catch (IOException e) {
            Log.e("DXGO", "Error in Reading: " + e.getLocalizedMessage());
            return null;
        }
    }

}