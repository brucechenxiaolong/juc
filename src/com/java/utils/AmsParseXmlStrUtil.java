package com.java.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;
import org.jdom.xpath.XPath;

/**
 * 解析xml字符串的类
 * @Title:AmsParseXmlStr.java
 * @Package:com.pde.ams.p9.xfire.offline
 * @date 2012-2-24 上午09:02:56
 * @version 1.0
 */
public class AmsParseXmlStrUtil {
    /**
     * 读取XML格式的字符串，根据属性来查找值
     * @param xmlStr 是要解析的xml格式的字符串
     * @param name 是在xml字符串中要获得标签属性
     * @return 返回的标签属性是name的值
     */
    @SuppressWarnings("unchecked")
    public static String returnElementValue(String xmlStr, String name){
        String value = "";
        StringReader sr = new StringReader(xmlStr);
        InputSource is = new InputSource(sr);
        SAXBuilder sb = new SAXBuilder();
        try{
            Document d = sb.build(is);
            Element e = d.getRootElement();
            List<Element> elementList = e.getChildren();
            for (Element element : elementList) {
                value = element.getChild(name).getTextTrim();
            }
        }catch(JDOMException e){
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
        return value;
    }
    /**
     * 读取XML格式的字符串，根据属性来查找某个节点的子节点的值List
     * @param xmlStr 是要解析的xml格式的字符串
     * @param name 是在xml字符串中要获得标签属性
     * @return 返回的标签属性是name的值
     */
    @SuppressWarnings("unchecked")
    public static List<String> returnElementValueList(String xmlStr, String name){
        StringReader sr = new StringReader(xmlStr);
        InputSource is = new InputSource(sr);
        SAXBuilder sb = new SAXBuilder();
        List<String> strList = null;
        try{
            strList = new ArrayList<String>();
            Document d = sb.build(is);
            Element root = d.getRootElement();
            List<Element> eList = root.getChildren();
            for (Element e : eList) {
                String value = e.getText();
                strList.add(value);
            }
        }catch(JDOMException e){
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
        return strList;
    }

    /**
     * 读取XML格式的字符串返回某个节点的属性值
     * @param xmlStr 是要解析的xml格式的字符串
     * @param name 是在xml字符串中要获得标签属性
     * @param attributeName name标签下中的属性名
     * @return 返回的标签属性是name的值
     */
    public static String returnAttributeValue(String xmlStr, String name, String attributeName){
        String value = "";
        StringReader sr = new StringReader(xmlStr);
        InputSource is = new InputSource(sr);
        SAXBuilder sb = new SAXBuilder();
        try{
            Document d = sb.build(is);
            Element e = d.getRootElement();
            value = e.getChild(name).getAttributeValue(attributeName);
        }catch(JDOMException e){
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 根据xml格式的字符串返回某个节点的子节点的属性值List
     * @param @param xmlStr 是要解析的xml格式的字符串
     * @param @param name 是在xml字符串中要获得标签属性
     * @param attributeName name标签下子标签中的属性名
     * @param @return
     * @return List<String>
     */
    @SuppressWarnings("unchecked")
    public static List<String> returnAttributeValueList(String xmlStr, String name, String attributeName){
        StringReader sr = new StringReader(xmlStr);
        InputSource is = new InputSource(sr);
        SAXBuilder sb = new SAXBuilder();
        List<String> strList = null;
        try {
            strList = new ArrayList<String>();
            Document d = sb.build(is);
            Element root = d.getRootElement();
            Element e = root.getChild(name);
            List<Element> aeList = e.getChildren();
            for (Element element : aeList) {
                String id = element.getAttributeValue(attributeName);
                strList.add(id);
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strList;
    }
    /**
     * 根据xml格式的字符串返回某个节点的子节点的属性值List
     * @param @param xmlStr 是要解析的xml格式的字符串
     * @param @param name 是在xml字符串中要获得标签属性
     * @param attributeName name标签的属性名
     * @param @return
     * @return List<String>
     */
    @SuppressWarnings("unchecked")
    public static List<String> returnAttributeValueList2(String xmlStr, String name, String attributeName){
        StringReader sr = new StringReader(xmlStr);
        InputSource is = new InputSource(sr);
        SAXBuilder sb = new SAXBuilder();
        List<String> strList = null;
        try {
            strList = new ArrayList<String>();
            Document d = sb.build(is);
            Element root = d.getRootElement();
            List<Element> eList = root.getChildren();
            for (int i = 0; i < eList.size(); i++) {//TABLE
                Element e = eList.get(i);
                if(e.getName().equals(name)){
                    String id = e.getAttributeValue(attributeName);
                    strList.add(id);
                }
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strList;
    }
    /**
     * 根据xml格式的字符串返回某个节点的子节点的属性值List
     * @param @param xmlStr 是要解析的xml格式的字符串
     * @param @param name 是在xml字符串中要获得标签属性
     * @param @param num 是在xml字符串中要获得某个标签
     * @param attributeName name标签下子标签中的属性名
     * @param @return
     * @return List<String>
     */
    @SuppressWarnings("unchecked")
    public static List<String> returnAttributeValueList(String xmlStr, String name, int num, String attributeName){
        StringReader sr = new StringReader(xmlStr);
        InputSource is = new InputSource(sr);
        SAXBuilder sb = new SAXBuilder();
        List<String> strList = null;
        try {
            strList = new ArrayList<String>();
            Document d = sb.build(is);
            Element root = d.getRootElement();
            List<Element> eList = root.getChildren();
//                for (int i = 0; i < eList.size(); i++) {//TABLE
            Element e = eList.get(num);
            if(e.getName().equals(name)){
                List<Element> aecList = e.getChildren();
                for (Element ec : aecList) {
                    String id = ec.getAttributeValue(attributeName);
                    strList.add(id);
                }
            }
//                }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strList;
    }

    public static String getElementValue(String xmlStr, String xpath){
        String value = "";
        StringReader sr = new StringReader(xmlStr);
        InputSource is = new InputSource(sr);
        SAXBuilder sb = new SAXBuilder();
        try{
            Document d = sb.build(is);
            Element e = d.getRootElement();
            Element element = (Element)XPath.selectSingleNode(e, xpath);
            if(element != null)
                value = element.getTextTrim();
        }catch(JDOMException e){
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }catch(RuntimeException e){

        }
        return value;
    }

    @SuppressWarnings("unchecked")
    public static List<String> getElementValueList(String xmlStr, String name){
        StringReader sr = new StringReader(xmlStr);
        InputSource is = new InputSource(sr);
        SAXBuilder sb = new SAXBuilder();
        List<String> strList = null;
        try{
            strList = new ArrayList<String>();
            Document d = sb.build(is);
            Element root = d.getRootElement();
            List<Element> eList = XPath.selectNodes(root, name);
            for (Element e : eList) {
                String value = e.getText();
                strList.add(value);
            }
        }catch(JDOMException e){
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
        return strList;
    }
}
