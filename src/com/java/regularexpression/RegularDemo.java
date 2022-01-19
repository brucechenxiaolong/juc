package com.java.regularexpression;

import java.nio.charset.Charset;
import java.util.regex.Pattern;

public class RegularDemo {
    public static void main(String[] args) {
        String str = "你好";
        Pattern pWord = Pattern.compile("[\u4e00-\u9fa5]");//校验中文的正则表达式
        System.out.println(Charset.defaultCharset());
        if(pWord.matcher(str).find() && str.length()>2) {//如果是中文，就只要前5个字，原因是dbf文件的列名只支持最多10个字符
            System.out.println("true");
        }
    }
}
