package com.java.jsonUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * java 操作 json格式的数据
 * 引用的jar是：fastjson-1.2.60.jar
 */
public class JsonDemo {

    //json字符串-简单对象
    private static String jsonStr = "{\"studentName\":\"张三\",\"studentAge\":18}";
    //json字符串-数组类型
    private static String jsonArrStr = "[{\"studentName\":\"张三\",\"studentAge\":18},{\"studentName\":\"李四\",\"studentAge\":17}]";
    //json字符串-复杂对象
    private static String complexJsonStr = "{\"teacherName\":\"李寻欢\",\"teacherAge\":30,\"course\":{\"courseName\":\"武术\",\"code\":110}, " +
            "\"students\":[{\"studentName\":\"张三\",\"studentAge\":18000},{\"studentName\":\"李四\",\"studentAge\":19000}]}";

    public static void main(String[] args) {
        // 1.JSON字符串到JSON对象的转换
        jsonStr2jsonObj();

        //2.JSON对象到JSON字符串的转换
        JSONObject parseObject = JSON.parseObject(jsonStr);
        System.out.println(parseObject.toJSONString());

        //3.JSON字符串到Java对象的转换
        jsonStr2javaObj();

        //4.Java对象到JSON字符串的转换
        Teacher teacher = new Teacher();
        teacher.setTeacherName("cxl");
        teacher.setTeacherAge(31);
        String jsonStr = JSON.toJSONString(teacher);
        System.out.println(jsonStr);
        //5.Java对象到JSON对象的转换
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        System.out.println(jsonObj.getString("teacherName") + "," + jsonObj.getInteger("teacherAge"));


        //6.json与map之间的转换
        HashMap<String,String> map = new HashMap<>();
        map.put("a","1");
        map.put("b","2");
        map.put("c","3");

        String json = JSON.toJSONString(map);//map转String
        JSONObject jsonObject = JSON.parseObject(json);//String转json

        //json转map
        Map<String, String> jsonMap = JSONObject.toJavaObject(jsonObject, Map.class);
        //String转map
        Map<String, String> jsonMap1 = JSONObject.parseObject(json, Map.class);


    }

    //3.JSON字符串到Java对象的转换
    private static void jsonStr2javaObj() {
        //（1）json字符串-简单对象与Java对象之间的转换
        //方式一：
        Student student = JSON.parseObject(jsonStr, new TypeReference<Student>(){});
        System.out.println(student.getStudentName() + "," + student.getStudentAge());
        //方式二：
        student = JSON.parseObject(jsonStr, Student.class);
        System.out.println(student.getStudentName() + "," + student.getStudentAge());

        //（2）json字符串-数组与Java对象之间的转换
        ArrayList<Student> studentList = JSON.parseObject(jsonArrStr, new TypeReference<ArrayList<Student>>(){});
        for (Student stu : studentList) {
            System.out.println(stu.getStudentName() + "," + stu.getStudentAge());
        }

        //（3）json字符串-复杂对象与Java对象之间的转换
        Teacher teacher = JSON.parseObject(complexJsonStr, new TypeReference<Teacher>(){});
        String teacherName = teacher.getTeacherName();
        int teacherAge = teacher.getTeacherAge();
        System.out.println(teacherName + "," + teacherAge);

        Course course = teacher.getCourse();
        System.out.println(course.getCourseName() + "," + course.getCode());
        List<Student> listStudent = teacher.getStudents();
        for (Student stu : listStudent) {
            System.out.println(stu.getStudentName() + "," + stu.getStudentAge());
        }
    }

    // 1.JSON字符串到JSON对象的转换
    private static void jsonStr2jsonObj() {

        //（1）json字符串-简单对象与JSONObject之间的转换
        JSONObject parseObject = JSON.parseObject(jsonStr);
        System.out.println(parseObject.getString("studentName") +","+ parseObject.getString("studentAge"));

        System.out.println("*************************华丽分隔符**********************************");

        //（2）json字符串-数组类型与JSONArray之间的转换
        JSONArray jsonArray = JSON.parseArray(jsonArrStr);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println(jsonObject.getString("studentName") +","+ jsonObject.getString("studentAge"));
        }

        System.out.println("*************************华丽分隔符**********************************");

        //（3）json字符串-复杂对象与JSONObject之间的转换
        JSONObject jsonObj = JSON.parseObject(complexJsonStr);
        System.out.println(jsonObj.getString("teacherName") + "," + jsonObj.getInteger("teacherAge"));

        JSONObject jsonChildObj = jsonObj.getJSONObject("course");
        System.out.println(jsonChildObj.getString("courseName") + "," + jsonChildObj.getInteger("code"));

        JSONArray jsonChildArr = jsonObj.getJSONArray("students");
        for (int i = 0; i < jsonChildArr.size(); i++) {
            JSONObject jsonObject = jsonChildArr.getJSONObject(i);
            System.out.println(jsonObject.getString("studentName") +","+ jsonObject.getString("studentAge"));
        }
    }

}
