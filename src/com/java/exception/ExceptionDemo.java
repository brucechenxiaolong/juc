package com.java.exception;

public class ExceptionDemo {
    public static void main(String[] args) {
        try {
            String code = "2";
            String rd = "";
            switch (code) {
                case "1":  //固化信息有效性检测
                    rd = SolidifyInfoCheck();
                    break;
                case "2":  //元数据项数据长度检测
                    rd = MetadataLengthCheck();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String MetadataLengthCheck() throws Exception{
        if(1 == 1){
            int i = 1/0;
        }
        return "";
    }

    private static String SolidifyInfoCheck() {

        return "";
    }
}
