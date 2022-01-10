package com.java.files;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件路径操作
 * 通过反射机制获取文件在jar包中的路径
 */
public class FilePathUtils {

    //获取target jar生成的路径：/D:/workspace/P10/JFJDAG/src/bec/b-services/archives-datamanagement-service/target/classes/
    public static final String SOURCE_SRC = FilePathUtils.class.getResource("/").getPath();

    //跟路径： C:-jfjdag-run
    public static final String ROOT_SRC = (System.getProperty("user.dir").length() == 1 ? "" :System.getProperty("user.dir")) + File.separator;

    //下载路径
    public static final String DOWNLOAD_PATH = ROOT_SRC + "read" + File.separator + "download" + File.separator + "offlineread" + File.separator;

    public static void main(String[] args) throws IOException {

        //SOURCE_SRC=/D:/workspace/test/juc/build/classes/
        System.out.println("SOURCE_SRC=" + SOURCE_SRC);

        //DOWNLOAD_PATH=D:\workspace\test\juc\read\download\offlineread\
        System.out.println("DOWNLOAD_PATH=" + DOWNLOAD_PATH);

        //重新生成新的文件:获取jar包中的文件路径：反射
        InputStream is = FilePathUtils.class.getClassLoader().getResourceAsStream(ROOT_SRC + "offlineread/offline");
        FileUtils.copyInputStreamToFile(is,new File(DOWNLOAD_PATH + "offline"));

    }
}
