package com.java.tree;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * p10产品树操作
 */
public class P10TreeUtil {

    public static void main(String[] args) {

        P10TreeUtil treeUtil = new P10TreeUtil();

        //1.-----------把list转换成tree-----------------
        /*List<CategoryClassPO> listPO = treeUtil.getCategoryClassPOList2();
        listPO = treeUtil.list2Tree(listPO);
        System.out.println(listPO);*/


        //2.-----------循环tree list 设置所有node_path值------------------------
        List<CategoryClassPO> listPO = treeUtil.getCategoryClassPOList2();
        listPO = treeUtil.list2Tree(listPO);
        treeUtil.listAppendNodePath(listPO, "");
        System.out.println(listPO);

        //3.--------------------???------------------------
        /*String split1 = ",";
        String split2 = ":";
        String value = "key1:value1,key2:value2,key5:value5";
        Assert.notNull(value, "获取档案库初始化配置失败！");
        List<String> values = new ArrayList<>();
        Arrays.stream(value.split(split1)).map(x -> x.split(split2).length < 2 ? new ArrayList<>() : Arrays.asList(x.split(split2)[1].split(""))).collect(Collectors.toList()).forEach(v -> values.addAll((Collection<? extends String>) v));
        System.out.println(values);
        if (values.stream().noneMatch(v -> v.equals("5"))) {
            System.out.println("4");
        }*/

        //递归查询所有父节点，通过当前数据id


    }


    //category_class_id=44444


    /**
     * 循环tree list 设置所有node_path值
     * @param list
     * @param nodePath
     */
    private void listAppendNodePath(List<CategoryClassPO> list, String nodePath) {
        list.forEach(category -> {
            String nodePathAll = "";
            if (StringUtils.isNotEmpty(nodePath)) {
                nodePathAll = nodePath + "/" + category.getNode_name();
            } else {
                nodePathAll = category.getNode_name();
            }
            category.setNode_path(nodePathAll);
            if (!CollectionUtils.isEmpty(category.getChilds())) {
                listAppendNodePath(category.getChilds(), category.getNode_path());
            }
        });
    }

    /**
     * 把list转换成tree
     * @param list
     * @return
     */
    public List<CategoryClassPO> list2Tree(List<CategoryClassPO> list) {
        //根据PID分组  list转 树结构

        //根据pid进行分组：key=pid；value=pid所在的条目列表
        Map<String, List<CategoryClassPO>> zoneByParentIdMap = list.stream().collect(Collectors.groupingBy(CategoryClassPO::getPid));
        //通过分组的pid，设置list的childs
        list.forEach(po -> po.setChilds(zoneByParentIdMap.get(po.getCategory_class_id())));
        //过滤掉 pid != 0 的数据
        List<CategoryClassPO> collect = list.stream().filter(po -> po.getPid().equals("0")).collect(Collectors.toList());
        return collect;
    }

























    /**
     * 初始化数据-死值
     * @return
     */
    public List<CategoryClassPO> getCategoryClassPOList2() {
        List<CategoryClassPO> list = new ArrayList<CategoryClassPO>();
        CategoryClassPO po1 = new CategoryClassPO();
        po1.setCategory_class_id("11111");
        po1.setPid("0");
        po1.setNode_name("xxx1");
        list.add(po1);

        po1 = new CategoryClassPO();
        po1.setCategory_class_id("22222");
        po1.setPid("0");
        po1.setNode_name("xxx2");
        list.add(po1);

        po1 = new CategoryClassPO();
        po1.setCategory_class_id("33333");
        po1.setPid("11111");
        po1.setNode_name("child_xxx1");
        list.add(po1);

        po1 = new CategoryClassPO();
        po1.setCategory_class_id("44444");
        po1.setPid("33333");
        po1.setNode_name("child_child_xxx1");
        list.add(po1);

        po1 = new CategoryClassPO();
        po1.setCategory_class_id("55555");
        po1.setPid("33333");
        po1.setNode_name("child_child_xxx2");
        list.add(po1);

        return list;
    }


    /**
     * 初始化tree数据-死值
     * @return
     */
    public List<CategoryClassPO> getCategoryClassPOList(){
        List<CategoryClassPO> list = new ArrayList<CategoryClassPO>();

        CategoryClassPO po1 = new CategoryClassPO();
        po1.setCategory_class_id("11111");
        po1.setPid("0");
        po1.setNode_name("xxx1");

        List<CategoryClassPO> childlist = new ArrayList<CategoryClassPO>();
        for (int i = 1; i <= 1; i++) {
            CategoryClassPO childPO = new CategoryClassPO();
            childPO.setCategory_class_id("" + i);
            childPO.setPid("11111");
            childPO.setNode_name("child_xxx" + i);
            childlist.add(childPO);
        }
        po1.setChilds(childlist);
        list.add(po1);


        CategoryClassPO po2 = new CategoryClassPO();
        po2.setCategory_class_id("22222");
        po2.setPid("0");
        po2.setNode_name("xxx2");

        childlist = new ArrayList<CategoryClassPO>();
        for (int i = 1; i <= 2; i++) {
            CategoryClassPO childPO = new CategoryClassPO();
            childPO.setCategory_class_id("" + i);
            childPO.setPid("22222");
            childPO.setNode_name("child_xxx" + i);
            childlist.add(childPO);
        }
        po2.setChilds(childlist);
        list.add(po2);

        return list;
    }
}
