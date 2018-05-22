package com.eways.elearning.Utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by nguyentandat on 11/28/17.
 */

public class FileUtils {
    public static String loadJSONFromAsset(Context context, String fileName) {
        String jsonStr = null;
        try {

            InputStream is = context.getAssets().open(fileName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            jsonStr = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonStr;

    }

    public static void write_file_internal(Context context, int mode, String fileName, String data) {

        try {

            // 2 lines of these are the same with one line below.
            // Notice:
            //      + Instead using getFilesDir(), you can use getCacheDir() to get directory
            // of cache place in internal storage.
            //      + openFileOutput will create in app private storage, not in cache.

//                File file = new File(context.getFilesDir(), fileName);
//                FileOutputStream fos = new FileOutputStream(file);

            FileOutputStream fos = context.openFileOutput(fileName, mode);

            // Instantiate a stream writer.
            OutputStreamWriter out = new OutputStreamWriter(fos);

            // Add data.
            if(mode == Context.MODE_APPEND) {
                out.append(data + "\n");
            }else {
                out.write(data);
            }

            // Close stream writer.
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String read_file_internal(Context context, String fileName) {

        try {

            // 2 lines of these are the same with one line below.
            // Notice:
            //      + Instead using getFilesDir(), you can use getCacheDir() to get directory
            // of cache place in internal storage.
            //      + openFileInput will open a file in app private storage, not in cache.

//            File file = new File(context.getFilesDir(), fileName);
//            FileInputStream fis = new FileInputStream(file);

            FileInputStream fis = context.openFileInput(fileName);

            // Instantiate a buffer reader. (Buffer )
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(fis));

            String s;
            StringBuilder fileContentStrBuilder = new StringBuilder();

            // Read every lines in file.
            while ((s = bufferedReader.readLine()) != null) {
                fileContentStrBuilder.append(s);
            }

            // Close buffer reader.
            bufferedReader.close();

            return fileContentStrBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    public boolean delete_file_internal(Context context, String fileName){
        // If the file is saved on internal storage, you can also ask the Context to locate and delete a file by calling deleteFile()
        return context.deleteFile(fileName);
    }

    /* ******************************************************************************************** *
     *                                                                                              *
     *  - If your app uses the WRITE_EXTERNAL_STORAGE permission, then it implicitly has permission *
     *  to read the external storage as well.                                                       *
     *  - Must declare READ_EXTERNAL_STORAGE or WRITE_EXTERNAL_STORAGE before manipulate with       *
     *  external storage.                                                                           *
     *  - Handle file in private external storage in low api (below 18), don't require permission.  *
     *  - Don't be confused external storage with SD external card, since internal SD card is       *
     *  considered as external storage. And internal SD card is a default external storage.         *
     *                                                                                              *
     * ******************************************************************************************** */

    // Checks if external storage is available for read and write.
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    // Checks if external storage is available to at least read.
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /** Write to a public external directory.
     *  @param mode:
     *          Context.MODE_APPEND     --> write more to exist file.
     *          Context.MODE_PRIVATE    --> only available within app.
     *          FileProvider with the FLAG_GRANT_READ_URI_PERMISSION    --> share file.
     *  @param mainDir: representing the appropriate directory on the external storage ( Environment.DIRECTORY_MUSIC, ...)
     *  @param subFolder: usually an app name to distinguish with another app.
     *  @param fileName: ".nomedia" + fileName to hide it from MediaStore scanning.
     */
    public static void write_file_external_public(String mainDir, String subFolder, int mode, String fileName, String data){

        if(!is_external_storage_available()){
//            Log.e(TAG, "External storage is not available.");
            return;
        }

        // Get the directory for the user's public mainDir directory.
        String directory = get_external_public_directory(mainDir, subFolder);
        File folder = new File(directory);

        // If directory doesn't exist, create it.
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, fileName);

//        Log.d(TAG, "File directory: " + file.getAbsolutePath());

        try {
            FileOutputStream fos;
            if(mode == Context.MODE_APPEND) {
                fos = new FileOutputStream(file, true);
            }else {
                fos = new FileOutputStream(file);
            }

            // Instantiate a stream writer.
            OutputStreamWriter out = new OutputStreamWriter(fos);

            // Add data.
            if(mode == Context.MODE_APPEND) {
                out.append(data + "\n");
            }else {
                out.write(data);
            }

            // Close stream writer.
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get_external_public_directory( String mainDir, String subFolder){

        File root;
        if(mainDir.isEmpty()){
            root = Environment.getExternalStorageDirectory();
        }else {
            root = Environment.getExternalStoragePublicDirectory(mainDir);
        }

        return root + File.separator  + subFolder;
    }

    /** Write to a public external directory.
     *  @param mode:
     *          Context.MODE_APPEND     --> write more to exist file.
     *          Context.MODE_PRIVATE    --> only available within app.
     *          FileProvider with the FLAG_GRANT_READ_URI_PERMISSION    --> share file.
     *  @param mainDir: - Representing the appropriate directory on the external storage ( Environment.DIRECTORY_MUSIC, ...)
     *                  - It can be null --> represent that directory as a parent file of private external storage in the app.
     *  @param subFolder: usually an app name to distinguish with another app.
     */
    public static void write_file_external_private(Context context, String mainDir, String subFolder, int mode, String fileName, String data){

        // Get the directory for the user's private mainDir directory.
        String directory = context.getExternalFilesDir(mainDir) + File.separator  + subFolder;
        File folder = new File(directory);

        // If directory doesn't exist, create it.
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, fileName);

        try {
            FileOutputStream fos;
            if(mode == Context.MODE_APPEND) {
                fos = new FileOutputStream(file, true);
            }else {
                fos = new FileOutputStream(file);
            }

            // Instantiate a stream writer.
            OutputStreamWriter out = new OutputStreamWriter(fos);

            // Add data.
            if(mode == Context.MODE_APPEND) {
                out.append(data + "\n");
            }else {
                out.write(data);
            }

            // Close stream writer.
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get_external_private_directory(Context context, String mainDir, String subFolder){
        return context.getExternalFilesDir(mainDir) + File.separator  + subFolder;
    }

    public String read_file_external_public(String mainDir, String subFolder, String fileName) {

        try {

            String directory = get_external_public_directory(mainDir, subFolder);
            File folder = new File(directory);

            File file = new File(folder, fileName);

            // If file doesn't exist.
            if (!file.exists()) {
//                Log.e(TAG, "File doesn't exist.");
                return null;
            }

            FileInputStream fis = new FileInputStream(file);

            // Instantiate a buffer reader. (Buffer )
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(fis));

            String s;
            StringBuilder fileContentStrBuilder = new StringBuilder();

            // Read every lines in file.
            while ((s = bufferedReader.readLine()) != null) {
                fileContentStrBuilder.append(s);
            }

            // Close buffer reader.
            bufferedReader.close();

            return fileContentStrBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    public String read_file_external_private(Context context, String mainDir, String subFolder, String fileName) {

        try {

            String directory = get_external_private_directory(context, mainDir, subFolder);
            File folder = new File(directory);

            File file = new File(folder, fileName);

            // If file doesn't exist.
            if (!file.exists()) {
//                Log.e(TAG, "File doesn't exist.");
                return null;
            }

            FileInputStream fis = new FileInputStream(file);

            // Instantiate a buffer reader. (Buffer )
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(fis));

            String s;
            StringBuilder fileContentStrBuilder = new StringBuilder();

            // Read every lines in file.
            while ((s = bufferedReader.readLine()) != null) {
                fileContentStrBuilder.append(s);
            }

            // Close buffer reader.
            bufferedReader.close();

            return fileContentStrBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    public static boolean is_external_storage_available(){
        if(!isExternalStorageWritable() || !isExternalStorageReadable()){
            return false;
        }

        return true;
    }

    public boolean delete_file_external_public(String mainDir, String subFolder, String fileName){

        String directory = get_external_public_directory(mainDir, subFolder);
        File folder = new File(directory);

        File file = new File(folder, fileName);

        // If file doesn't exist.
        if (!file.exists()) {
//            Log.e(TAG, "File doesn't exist.");
            return true;
        }
        return file.delete();
    }

    public boolean delete_file_external_private(Context context, String mainDir, String subFolder, String fileName){

        String directory = get_external_private_directory(context, mainDir, subFolder);
        File folder = new File(directory);

        File file = new File(folder, fileName);

        // If file doesn't exist.
        if (!file.exists()) {
//            Log.e(TAG, "File doesn't exist.");
            return true;
        }
        return file.delete();
    }

    public static ArrayList<File> get_external_sd_card_directory(){

        // Retrieve the primary External Storage (internal sd card).
        final File primaryExternalStorage = Environment.getExternalStorageDirectory();

        // Primary external storage (internal sd card) usually has path: [storage]/emulated/0
        File externalStorageRoot = primaryExternalStorage.getParentFile().getParentFile();

        // Get list folders under externalStorageRoot (which includes primaryExternalStorage folder).
        File[] files = externalStorageRoot.listFiles();

        ArrayList<File> listStorage = new ArrayList<>();

        for (File file : files) {

            // it is a real directory (not a USB drive)...
            if ( file.isDirectory() && file.canRead() && (file.listFiles().length > 0) ) {
                listStorage.add(file);
            }
        }

        return listStorage;
    }

    public static void write_file(File directory, String fileName, int mode, String data){
        try {

            // Create file.
            File file = new File(directory, fileName);

            FileOutputStream fos;
            if(mode == Context.MODE_APPEND) {
                fos = new FileOutputStream(file, true);
            }else {
                fos = new FileOutputStream(file);
            }

            // Instantiate a stream writer.
            OutputStreamWriter out = new OutputStreamWriter(fos);

            // Add data.
            if(mode == Context.MODE_APPEND) {
                out.append(data + "\n");
            }else {
                out.write(data);
            }

            // Close stream writer.
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String read_file(File directory, String fileName, int mode, String data){
        try {

            // Create file.
            File file = new File(directory, fileName);

            FileInputStream fis = new FileInputStream(file);

            // Instantiate a buffer reader. (Buffer )
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(fis));

            String s;
            StringBuilder fileContentStrBuilder = new StringBuilder();

            // Read every lines in file.
            while ((s = bufferedReader.readLine()) != null) {
                fileContentStrBuilder.append(s);
            }

            // Close buffer reader.
            bufferedReader.close();

            return fileContentStrBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String read_file(File file, int mode, String data){
        try {

            FileInputStream fis = new FileInputStream(file);

            // Instantiate a buffer reader. (Buffer )
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(fis));

            String s;
            StringBuilder fileContentStrBuilder = new StringBuilder();

            // Read every lines in file.
            while ((s = bufferedReader.readLine()) != null) {
                fileContentStrBuilder.append(s);
            }

            // Close buffer reader.
            bufferedReader.close();

            return fileContentStrBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
