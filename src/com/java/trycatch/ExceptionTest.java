package com.java.trycatch;

/**
 * 异常捕获的用法
 */
public class ExceptionTest {

    /**
     * 捕获异常并输出
     * @param args
     */
    public static void main(String[] args) {
        ExceptionTest test = new ExceptionTest();
        try {
            test.xx();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 自定义异常类，并抛出异常
     * @throws PdeException
     */
    private void xx() throws PdeException{
        if(1 == 2){
            System.out.println();
        }else{
            throw new PdeException(PdeExceptionEnum.UNKNOW_ERROR);
        }
    }

}
