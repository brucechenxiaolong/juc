package com.java.stringbuffer;

public class BufferTest {
    public static void main(String[] args) {

        StringBuffer schemeClassIds = new StringBuffer();
        for (int i = 1; i <= 3; i++) {
            String schemeClassId = "xxx" + i;
            if("".equals(schemeClassIds.toString())){
                schemeClassIds.append("'").append(schemeClassId).append("'");
            }else {
                schemeClassIds.append(",'").append(schemeClassId).append("'");
            }

        }

        System.out.println(schemeClassIds.toString());

    }
}
