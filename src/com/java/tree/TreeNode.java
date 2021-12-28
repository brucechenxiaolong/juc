package com.java.tree;

public class TreeNode {
    /**
     * 节点id
     */
    private String id;
    /**
     * 父节点 默认0为根节点
     */
    private String parentId;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 是否有子节点
     */
    private boolean hasChild;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    /**
     *
     * @param id
     * @param parentId
     * @param name
     */
    public TreeNode(String id, String parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }
}