package com.java.utils.ZIP;

import com.java.aes.Aes;
import com.java.utils.StringUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.poi.util.IOUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @Author huqk
 * @Date 2021/5/8 17:10
 * @Version 1.0
 */
public abstract class FileUtil {


    /**
     * 预遮盖变遮盖
     *
     * @param address ofd地址    @Value("${ofd.url}")
     * @param file    预遮盖文件
     * @return 遮盖后的文件流
     */
    public static InputStream pre2cover(String address, File file) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(address + "/viewer/api/file/preCover/confirm");
        CloseableHttpResponse response = null;
        ByteArrayOutputStream outputStream = null;
        try {
            httpPost.setEntity(MultipartEntityBuilder.create().addPart("file", new FileBody(file)).build());
            response = client.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                InputStream inputStream = responseEntity.getContent();
                outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) > -1) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.flush();
                return new ByteArrayInputStream(outputStream.toByteArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (client != null) {
                    client.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                // 释放资源
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        InputStream is = pre2cover("http://192.168.12.150:9017", new File("D:/123/ofd/123-1.ofd"));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int r = 0;
        while ((r = is.read(buffer)) > 0) {
            output.write(buffer, 0, r);
        }
        FileOutputStream fos = new FileOutputStream("D:/123/ofd/112.ofd");
        output.writeTo(fos);
        output.flush();
        output.close();
        fos.close();
    }

    /**
     * 删除文件/文件夹(同时删除根目录)
     * @param path
     * @return boolean 如果出错返回false
     */
    public static boolean delete(String path) {
        File file = new File(path);
        // 如果文件/文件夹不存在, 则不执行删除操作
        if (!file.exists()) {
            return true;
        }
        return clearFolder(path) && file.delete();
    }

    /**
     * 清空文件夹(不删除根目录)
     * @param path
     * @return boolean 如果出错返回false
     */
    public static boolean clearFolder(String path) {
        File file = new File(path);
        // 如果文件/文件夹不存在, 则不执行删除操作
        if (!file.exists()) {
            return true;
        }
        // 如果是文件
        if (file.isFile()) {
            return true;
        }
        // 如果是文件夹
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isFile()) {
                if (!childFile.delete()) {
                    return false;
                }
            } else {
                if (!delete(childFile.getAbsolutePath())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 创建本地文件夹
     * @param folderPath
     * @return
     */
    public static boolean mkdirs(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists()) {
            return folder.isDirectory();
        } else {
            return folder.mkdirs();
        }
    }

    /**
     * 将流复制到新文件
     * @param is 基础文件流
     * @param newFile 文件名（必须带有后缀）
     * @return
     */
    public static boolean copyFile(InputStream is, String newFile) {
        //BufferedInputStream bis = null;
        OutputStream os = null;
        //BufferedOutputStream bos = null;
        // 建立文件夹
        if (!mkdirs(new File(newFile).getParent())) {
            return false;
        }
        try {
            // 获取输入流
            //bis = new BufferedInputStream(is);
            os = new FileOutputStream(newFile);
            //	bos = new BufferedOutputStream(os);
//					// 保存文件
//					int size = 0;
//					byte[] buf = new byte[1024 * 4];
//					while ((size = bis.read(buf)) != -1) {
//						bos.write(buf, 0, size);
//					}
            IOUtils.copy(is, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            //close(bos);
            close(os);
            //close(bis);
            close(is);
        }
        return true;
    }

    /**
     * 关闭流
     * @param os
     */
    public static void close(Closeable... os) {
        if (os != null) {
            for (Closeable o : os) {
                if (o != null) {
                    if (o instanceof Flushable) {
                        try {
                            ((Flushable) o).flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        o.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 关闭流(不输出错误信息到控制台)
     * @param os
     */
    public static void closeQuiet(Closeable... os) {
        if (os != null) {
            for (Closeable o : os) {
                if (o != null) {
                    if (o instanceof Flushable) {
                        try {
                            ((Flushable) o).flush();
                        } catch (IOException e) {
                        }
                    }
                    try {
                        o.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

    /**
     * 将文件复制到新文件
     * @param oldFile
     * @param newFile（必须带有后缀）
     * @return
     */
    public static boolean copyFile(String oldFile, String newFile) {
        if (!new File(oldFile).exists()) {
            return false;
        }

        InputStream is = null;
        try {
            is = new FileInputStream(oldFile);
            return copyFile(is, newFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(is);
        }
    }

    /**
     * 将文件复制到新文件
     * @param oldFile
     * @param newFile（必须带有后缀）
     * @param isEncrypt
     * @param cipherType
     * @return
     */
    public static boolean copyFile(String oldFile, String newFile, boolean isEncrypt, String encryptKey,int cipherType) {
        if (!new File(oldFile).exists()) {
            return false;
        }
        InputStream is = null;
        try {
            is = new FileInputStream(oldFile);
            if(isEncrypt){
                Cipher cipher = Aes.initAESCipher(encryptKey,cipherType);
                CipherInputStream cipherInputStream = new CipherInputStream(is, cipher);
                return copyFile(cipherInputStream, newFile);
            } else {
                return copyFile(is, newFile);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(is);
        }
    }

    /**
     * 复制文件夹
     * @param oldFolder 源文件夹
     * @param newFolder 目标文件夹
     */
    public static boolean copyFolder(String oldFolder, String newFolder) {
        // 检查旧文件夹是否存在
        File old = new File(oldFolder);
        if (!old.exists() || !old.isDirectory()) {
            return true;
        }
        // 新建目标目录
        mkdirs(newFolder);
        // 复制源文件夹当前下的文件或目录
        for (File file : old.listFiles()) {
            String oldPath = file.getAbsolutePath();
            String newPath = new File(newFolder, file.getName()).getAbsolutePath();
            if (file.isFile()) {
                if (!copyFile(oldPath, newPath)) {
                    delete(newPath);
                    return false;
                }
            } else {
                if (!copyFolder(oldPath, newPath)) {
                    delete(newPath);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 移动文件到指定目录
     * @param oldPath 如：c:/fqf.txt
     * @param newPath 如：d:/fqf.txt
     */
    public static boolean renameFile(String oldPath, String newPath) {
        File file = new File(oldPath);
        if (!file.exists()) {
            return false;
        }
        return file.renameTo(new File(newPath));
    }

    /**
     * 根据文件路径(含文件名及扩展名)获取文件名(含扩展名)
     * @param fileNamePath
     * @return String
     */
    public static String getFileName(String fileNamePath) {
        return new File(fileNamePath).getName();
    }

    /**
     * 根据文件路径(含文件名及扩展名)获取根目录
     * @param fileNamePath
     * @return String
     */
    public static String getRootDirctory(String fileNamePath) {
        String[] fileNamePathArray = StringUtil.toArray(fileNamePath, "/");
        return fileNamePathArray[0];
    }

    /**
     * 根据文件路径(含文件名及扩展名)过滤扩展名
     * @param fileNamePath
     * @return String
     */
    public static String getFilterExt(String fileNamePath) {
        int lastIndex = fileNamePath.lastIndexOf('.');
        if (lastIndex != -1) {
            return fileNamePath.substring(0, lastIndex);
        } else {
            return fileNamePath;
        }
    }

    /**
     * 根据文件路径(含文件名及扩展名)获取扩展名
     * @param filename 文件名
     * @return
     */
    public static String getExtension(String filename) {
        return getExtension(filename, "");
    }

    /**
     * 根据文件路径(含文件名及扩展名)获取扩展名
     * @param filename 文件名
     * @param defExt 默认扩展名
     * @return
     */
    public static String getExtension(String filename, String defExt) {
        if (filename != null && filename.length() > 0) {
            int i = filename.lastIndexOf('.');

            if (i > -1 && i < filename.length() - 1) {
                return filename.substring(i + 1);
            }
        }
        return defExt;
    }

    /**
     * 过滤第一个文件夹
     * @param filePath
     * @return String
     */
    public static String getFilterRootDir(String filePath) {
        String[] folders = filePath.split("/");
        int a = 1;
        if (folders[0].trim().length() == 0) {
            a = 2;
        }
        StringBuffer path = new StringBuffer();
        for (int i = a; i < folders.length; i++) {
            path.append("/").append(folders[i]);
        }
        return path.toString();
    }

    /**
     * 获取存储文件
     * @param uuid   文件标识
     * @return File
     */
    public static File getStoreFile(String uuid) {
        String path = getDir();
        return new File(path + File.separator + uuid);
    }
    public static File getStoreZipFile(String uuid) {
        String path = getFileDir();
        return new File(path + File.separator + uuid);
    }


    /**
     * 获取导入目录
     * @return
     */
    public static String getDir() {
        String path = System.getProperty("user.dir") + File.separator + "temp" + File.separator + "import";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return path;
    }

    /**
     *
     * @return
     */
    public static String getFileDir() {
        String path = System.getProperty("user.dir") + File.separator + "temp" + File.separator + "importFile";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return path;
    }

    /**
     *
     * @return
     */
    public static String getExportDir(String batchId) {
        String path = System.getProperty("user.dir") + File.separator + "temp" + File.separator + "export"+ File.separator + batchId;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return path;
    }

    public static String getExportDir() {
        String path = System.getProperty("user.dir") + File.separator + "temp" + File.separator + "export" ;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return path;
    }

    /**
     * 路径类参数列表
     */
    private Map<String,String> pathParamsMap = new HashMap<String,String>();
    /**
     * 得到校正的路径参数
     * 如：key=d:/p9_new_2019/
     */
    public String getPathValue(String key){
        if(!pathParamsMap.containsKey(key)){
            String value = this.getPathValue(key);
            if(value==null) return null;
            value = value.replace("/", File.separator).replace("\\\\", File.separator);
            if (!value.endsWith(File.separator)) {
                value += File.separator;
            }
            pathParamsMap.put(key, value);
        }
        return pathParamsMap.get(key);
    }

}
