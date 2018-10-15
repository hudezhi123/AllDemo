package com.first.hdz.qq.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * created by hdz
 * on 2018/9/7
 */
public class FileUtils {

    private static String TAG = "FileUtils";

    private static boolean IsInit = false;

    //文件写入的本地的存储方式：
    public static final int LOCAL_EXTERNAL = 0;      //外部存储
    private static final int LOCAL_INTERNAL = 1;     //内部存储

    private static int mStorageType = 1;
    private static Context mContext;

    private static final String APK_NAME = "qq";    //apk的标识符

    public static final String DIR_TYPE_IMG = "/" + APK_NAME + "/img/";  //图片文件
    public static final String DIR_TYPE_DOC = "/" + APK_NAME + "/doc/";  //文本文件
    public static final String DIR_TYPE_APK = "/" + APK_NAME + "/apk/";  //apk文件

    /**
     * 只能初始化一次，最好在 Application 中
     *
     * @param context
     * @param storageType
     * @throws Exception
     */
    public static void init(Context context, int storageType) throws Exception {
        if (IsInit) {
            throw new DuplicateException(TAG);
        }
        IsInit = true;
        mContext = context.getApplicationContext();
        if (storageType > 2 || storageType < 1) {
            storageType = Math.abs(storageType) % 2;
        }
        mStorageType = storageType;
    }


    public static double getFileSize(File file) throws Exception {
        if (!IsInit) {
            throw new NotInitException(TAG);
        }
        long size = 0;
        if (file.isDirectory()) {
            throw new IOException("It's dir,not file exactly");
        }
        if (file.exists()) {
            FileInputStream input = new FileInputStream(file);
            size = input.available();
        } else {
            throw new FileNotFoundException(file.getName() + " file is not exist");
        }
        return size;
    }


    /**
     * First of all,you need to get the permission to access to ExternalStorage,
     * maybe the read or write
     *
     * @param path       文件路径
     * @param fileName   文件名
     * @param dataSource 即将写入文件的source
     */
    private static String writeToLocal(String path, String fileName, byte[] dataSource) {
        if (dataSource == null) {
            return "";
        }
        InputStream bis = new ByteArrayInputStream(dataSource);
        FileOutputStream output = null;
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File file = new File(fileDir, fileName);
        if (file.exists()) {
            file.delete();
        } else {
            try {
                file.createNewFile();
                output = new FileOutputStream(file);
                int len = 0;
                byte[] buffer = new byte[1024 * 4];
                while ((len = bis.read(buffer)) != -1) {
                    output.write(buffer, 0, len);
                    output.flush();
                }
                return file.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 写文件
     *
     * @param fileName
     * @param dataSource
     * @param fileType
     * @throws Exception
     */
    public static void writeFile(String fileName, byte[] dataSource, String fileType) throws Exception {
        if (!IsInit) {
            throw new NotInitException(TAG);
        }
        switch (mStorageType) {
            case LOCAL_EXTERNAL:
                writeToSD(fileName, dataSource, fileType);
                break;
            case LOCAL_INTERNAL:
                writeToInternal(mContext, fileName, dataSource, fileType);
                break;
        }
    }


    /**
     * 将文件保存在设备本地
     *
     * @param fileName   文件名称
     * @param dataSource 文件源
     * @return 文件路径
     */
    public static String writeToSD(String fileName, byte[] dataSource, String fileType) throws Exception {
        if (IsInit) {
            throw new DuplicateException(TAG);
        }
        String filePath = null;
        try {
            filePath = mkDir(null, fileType, LOCAL_EXTERNAL);
            return writeToLocal(filePath, fileName, dataSource);

        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
        }
        return "";
    }

    /**
     * 将文件保存到内部存储上
     *
     * @param context
     * @param fileName
     * @param dataSource
     * @param fileType
     */
    public static void writeToInternal(Context context, String fileName, byte[] dataSource, String fileType) throws Exception {
        if (IsInit) {
            throw new DuplicateException(TAG);
        }
        try {
            String filePath = mkDir(context, fileType, LOCAL_INTERNAL);
            writeToLocal(filePath, fileName, dataSource);
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }


    /**
     * 直接通过 context.openFileOutput 获取输出文件流，一般不用
     *
     * @param fileName 文件名称
     * @param content  存储内容
     * @param context  上下文
     */
    public static void saveToInternal(String fileName, String content, Context context) throws Exception {
        if (!IsInit) {
            throw new NotInitException(TAG);
        }
        if (fileName == null || "".equals(fileName)) {
            return;
        }
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static byte[] readFile(String fileName, String fileType) throws Exception {
        if (!IsInit) {
            throw new NotInitException(TAG);
        }
        byte[] data = null;
        switch (mStorageType) {
            case LOCAL_EXTERNAL:
                data = loadFileFromSD(fileName, fileType, LOCAL_EXTERNAL);
                break;
            case LOCAL_INTERNAL:
                data = loadFileFromInternal(mContext, fileName, fileType, LOCAL_INTERNAL);
                break;
        }
        return data;
    }

    public static byte[] loadFileFromLocal(String filePath, String fileName) throws Exception {
        File file = new File(filePath, fileName);
        if (isFileExist(file)) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            InputStream fis = new FileInputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024 * 2];
            while ((len = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
                bos.flush();
            }
            return bos.toByteArray();
        }
        return null;
    }

    private static byte[] loadFileFromSD(String fileName, String fileType, int fromWhere) throws Exception {
        if (IsSDAvailable()) {
            String filePath = mkDir(null, fileType, fromWhere);
            return loadFileFromLocal(filePath, fileName);
        } else {
            throw new NotAccessibleException(TAG, "it's denied to access to sdcard!");
        }
    }

    private static byte[] loadFileFromInternal(Context context, String fileName, String fileType, int fromWhere) throws Exception {
        String filePath = mkDir(context, fileType, fromWhere);
        return loadFileFromLocal(filePath, fileName);
    }


    private static boolean isFileExist(File file) {
        if (file.exists()) {
            if (file.length() <= 0) {
                file.delete();
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    /**
     * @param context  内部存储的上下文，如果是外部存储则 直接为 @param null
     * @param fileType 文件类型
     * @param toWhere  内部存储，还是外部存储
     * @return 创建目录并且返回目录地址
     */
    public static String mkDir(Context context, String fileType, int toWhere) throws Exception {
        String filePath = "";
        String rootDir = "";
        switch (toWhere) {
            case LOCAL_EXTERNAL:
                try {
                    rootDir = getSDRooDir();
                    File path = new File(rootDir + fileType);
                    if (!path.exists()) {
                        if (!path.mkdirs()) {
                            throw new Exception("it's fail to make dir！");
                        }
                    }
                    filePath = path.getAbsolutePath();
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
                break;
            case LOCAL_INTERNAL:
                rootDir = context.getFilesDir().getAbsolutePath();
                File path = new File(rootDir + fileType);
                if (!path.exists()) {
                    if (!path.mkdirs()) {
                        throw new Exception("it's fail to make dir！");
                    }
                }
                filePath = path.getAbsolutePath();
                break;
        }
        return filePath;
    }

    /**
     * sdcard 是否可以访问
     *
     * @return true 可以   false 不行
     */
    public static boolean IsSDAvailable() throws Exception {
        if (IsInit) {
            throw new DuplicateException(TAG);
        }
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取sdcard 根目录地址
     *
     * @return 返回sdcard的根目录
     * @throws Exception 如果sdcard不能访问，则抛出异常,或者没有初始化
     */
    public static String getSDRooDir() throws Exception {
        if (IsInit) {
            throw new DuplicateException(TAG);
        }
        if (IsSDAvailable()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            throw new NotAccessibleException(TAG, "it's denied to access to sdcard!");
        }
    }
}
