package com.java.tree;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class TreeUtil {

//    public static Map<String,Object> mapArray = new LinkedHashMap<String, Object>();

    public List<TreeNode> menuCommon;
    public List<Object> list = new ArrayList<Object>();


    public  static void main(String[] args){

        TreeUtil treeUtil = new TreeUtil();

        List<TreeNode> treeNodeList = new ArrayList<>();
        TreeNode treeNode1 = new TreeNode("1","0","首页");
        TreeNode treeNode2 = new TreeNode("2","0","订单");
        TreeNode treeNode3 = new TreeNode("3","1","预约");
        TreeNode treeNode4 = new TreeNode("4","2","捐献");
        TreeNode treeNode5 = new TreeNode("5","4","我的订单");
        TreeNode treeNode6 = new TreeNode("6","5","个人中心");
        treeNodeList.add(treeNode1);
        treeNodeList.add(treeNode6);
        treeNodeList.add(treeNode5);
        treeNodeList.add(treeNode3);
        treeNodeList.add(treeNode4);
        treeNodeList.add(treeNode2);

        System.out.println(JSONObject.toJSONString(treeUtil.treeMenu(treeNodeList)));
        System.out.println(JSONObject.toJSONString(treeUtil.menuChild("1")));

    }

    /**
     * 把list转json
     * @param menu
     * @return
     */
    public List<Object> treeMenu(List<TreeNode> menu){
        this.menuCommon = menu;
        for (TreeNode treeNode : menu) {
            Map<String,Object> mapArr = new LinkedHashMap<String, Object>();
            if(treeNode.getParentId().equals("0")){
                setTreeMap(mapArr,treeNode);
                list.add(mapArr);
            }
        }
        return list;
    }

    /**
     * 获取当前节点下的所有子节点
     * @param id
     * @return
     */
    public List<?> menuChild(String id){
        List<Object> lists = new ArrayList<Object>();
        for(TreeNode a:menuCommon){
            Map<String,Object> childArray = new LinkedHashMap<String, Object>();
            if(a.getParentId() .equals(id)){
                setTreeMap(childArray,a);
                lists.add(childArray);
            }
        }
        return lists;
    }

    private void setTreeMap(Map<String,Object> mapArr,TreeNode treeNode){
        mapArr.put("id", treeNode.getId());
        mapArr.put("name", treeNode.getName());
        mapArr.put("parentId", treeNode.getParentId());
        List<?> childrens = menuChild(treeNode.getId());
        if(childrens.size()>0){
            mapArr.put("hasChild",true);
        }else{
            mapArr.put("hasChild",false);
        }
        mapArr.put("childrens", menuChild(treeNode.getId()));
    }

}
