package com.java.tree;

import java.util.ArrayList;
import java.util.List;

public class CategoryClassPO {

    private String category_class_id;
    private String node_name;
    private String pid;
    private String node_path;

    private List<CategoryClassPO> childs = new ArrayList<CategoryClassPO>();

    public String getCategory_class_id() {
        return category_class_id;
    }

    public void setCategory_class_id(String category_class_id) {
        this.category_class_id = category_class_id;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public List<CategoryClassPO> getChilds() {
        return childs;
    }

    public void setChilds(List<CategoryClassPO> childs) {
        this.childs = childs;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getNode_path() {
        return node_path;
    }

    public void setNode_path(String node_path) {
        this.node_path = node_path;
    }
}
