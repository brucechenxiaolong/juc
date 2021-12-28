package com.java.test;

import java.util.ArrayList;
import java.util.List;

/**
 * c:for 多个循环嵌套，通过c:for 可以跳转到最外层循环。
 */
public class CFor {
    public static void main(String[] args) {
        CFor cfor = new CFor();
        cfor.zzz();
    }


    public void xxx(){
        List<String> usersNotSort = new ArrayList<String>();
        usersNotSort.add("test001");
        usersNotSort.add("test002");
        usersNotSort.add("test003");

        c:for (int i = 0; i < usersNotSort.size(); i++) {
            String xxx = usersNotSort.get(i);
            System.out.println(xxx);
            if("test002".equals(xxx)){
                break c;
            }
        }
    }

    public void yyy(){
        List<String> usersNotSort = new ArrayList<String>();
        usersNotSort.add("test001");
        usersNotSort.add("test002");
        usersNotSort.add("test003");
        usersNotSort.add("test006");

        List<String> list2 = new ArrayList<String>();
        list2.add("test003");
        list2.add("test004");
        list2.add("test005");

        for (int i = 0; i < usersNotSort.size(); i++) {
            String xxx = usersNotSort.get(i);
            System.out.println("***xxx=" + xxx + "***");

            c:for (int j = 0; j < list2.size(); j++) {
                String yyy = list2.get(j);
                System.out.println(yyy);
                if(xxx.equals(yyy)){
                    break c;
                }
            }
        }
    }

    public void zzz(){
        outerCycle:
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(j == 3){
                    continue outerCycle;
                }
                System.out.print("("+i+","+j+") ");
            }
            System.out.println();
        }
    }

}
