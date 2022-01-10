package com.java.files;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

public class FileUtils {

    private static String ETL_TEMP_FILES_PATH = "D:\\P10\\etl_temp_files\\";

    public static final String TYPE_JPG = "jpg";
    public static final String TYPE_GIF = "gif";
    public static final String TYPE_PNG = "png";
    public static final String TYPE_BMP = "bmp";
    public static final String TYPE_UNKNOWN = "unknown";

    public static final String FORMAT_FILE_NAME = "%d.%s";

    private static byte[] b;

    /**
     * @param fis 文件输入流
     * @return
     * @description: 根据文件流判断图片类型
     * @author: Jeff
     * @date: 2019年12月7日
     */
    public static String getPicType(InputStream fis) {
        // 读取文件的前几个字节来判断图片格式
        b = new byte[4];
        try {
            fis.read(b, 0, b.length);
            String type = bytesToHexString(b).toUpperCase();
            if (type.contains("FFD8FF")) {
                return TYPE_JPG;
            } else if (type.contains("89504E47")) {
                return TYPE_PNG;
            } else if (type.contains("47494638")) {
                return TYPE_GIF;
            } else if (type.contains("424D")) {
                return TYPE_BMP;
            } else {
                return type;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param src
     * @return
     * @description: byte数组转换成16进制字符串
     * @author: Jeff
     * @date: 2019年12月7日
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * @param dir
     * @description: 判断文件夹是否存在，不存在则创建
     * @author: Jeff
     * @date: 2019年12月7日
     */
    public static void isDirPathExist(File dir) {
        // 判断文件夹是否存在
        if (dir.isDirectory()) {
            System.out.println(dir + "文件夹已存在");
        } else {
            System.out.println(dir + "文件夹不存在");
            dir.mkdir();
            System.out.println("创建文件夹" + dir);
        }
    }

    /**
     * @description: 从服务器获得图片输入流InputStream, 并写到本地磁盘
     * @author: Jeff
     * @date: 2019年12月7日
     */
    public static void saveImageToDisk(InputStream inputStream, String diskPath) {
        // 根据文件流判断图片类型，此处不要关闭流,要不然下方获取不到输入流
        String picType = FileUtils.getPicType(inputStream);
        System.out.println("文件格式为：" + picType);
        String fileName = String.format(FORMAT_FILE_NAME, new Date().getTime(), picType);
        System.out.println("文件名字为：" + fileName);
        byte[] data = new byte[1024];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        try {
            File dirFile = new File(diskPath);
            // 判断文件夹是否存在，不存在则创建
            FileUtils.isDirPathExist(dirFile);
            fileOutputStream = new FileOutputStream(new File(dirFile, fileName));
            // 首先写入判断文件格式的前几个字节
            fileOutputStream.write(b, 0, b.length);
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        System.out.println(fileName + "写到本地磁盘完成");
    }

    /**
     * @return
     * @description: 从服务器获得一个输入流(本例是指从服务器获得一个image输入流)
     * @author: Jeff
     * @date: 2019年12月7日
     */
    public static InputStream getInputStream(String urlPath) {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(urlPath);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置网络连接超时时间
            httpURLConnection.setConnectTimeout(3000);
            // 设置应用程序要从网络连接读取数据
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                // 从服务器返回一个输入流
                inputStream = httpURLConnection.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }


    /**
     * @return 返回下载文件
     * @throws Exception
     */
    public static void downloadZip() {
        String uuid = UUID.randomUUID().toString();
        String outPath = System.getProperty("user.dir") + File.separator + "zip" + File.separator + uuid + File.separator;
        File dir = new File(outPath);
        dir.mkdirs();


    }




    /**
     * 从网上下载图片到本地磁盘中
     * @param args
     */
    public static void main(String[] args) {

//        String outPath = ETL_TEMP_FILES_PATH + File.separator;
//        System.out.println(outPath);
//        String filePath = "/BSYJ/20211124/归档信息包1637715767821.zip";
//        String[] filePathArray = filePath.split("\\/");
//        String fileName = filePathArray[filePathArray.length-1];
//        String unzipPath = outPath + fileName.replace(".zip", "");
//        unzipPath = unzipPath.replace(".ZIP", "");

        //删除zip和解压后的原文
//        clearFiles(ETL_TEMP_FILES_PATH + "\\" + "归档信息包1637756326949.zip");

        String separator = "/|\\\\";//此分隔符支持windows

        String originalFile = "D:\\workspace\\P10\\JFJDAG\\src\\bec\\temp\\etl_temp_files\\归档信息包1637888855070\\001\\11111";
        String[] filePathArray = originalFile.split(separator);
        String fileName = filePathArray[filePathArray.length-1];
        System.out.println(fileName);

    }


    public static void clearFiles(String workspaceRootPath){
        File file = new File(workspaceRootPath);
        deleteFile(file);
    }

    public static void deleteFile(File file){
        if(file.exists()){
            if(file.isDirectory()){
                File[] files = file.listFiles();
                for(int i=0; i<files.length; i++){
                    deleteFile(files[i]);
                }
            }
        }
        file.delete();
    }

}
