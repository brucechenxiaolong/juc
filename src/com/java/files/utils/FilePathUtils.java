package com.java.files.utils;

import com.java.sqlite.P10CSqliteUtil;

public class FilePathUtils {

    //获取target jar生成的路径：/D:/workspace/P10/JFJDAG/src/bec/b-services/archives-datamanagement-service/target/classes/
    public static final String SOURCE_SRC = FilePathUtils.class.getResource("/").getPath();

    public static void main(String[] args) {

        System.out.println("SOURCE_SRC=" + SOURCE_SRC);
    }
}
