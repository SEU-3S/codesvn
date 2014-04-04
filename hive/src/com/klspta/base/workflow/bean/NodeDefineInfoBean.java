package com.klspta.base.workflow.bean;

/**
 * <br>Title:节点定义信息
 * <br>Description:
 * <br>Author:郭润沛
 * <br>Date:2011-11-4
 */
public class NodeDefineInfoBean {
private String wfID;
private String nodeName;
private int x = 0;
private int y = 0;
private int width = 0;
private int height = 0;
public String getWfID() {
    return wfID;
}
public void setWfID(String wfID) {
    this.wfID = wfID;
}
public String getNodeName() {
    return nodeName;
}
public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
}
public int getX() {
    return x;
}
public void setX(int x) {
    this.x = x;
}
public int getY() {
    return y;
}
public void setY(int y) {
    this.y = y;
}
public int getWidth() {
    return width;
}
public void setWidth(int width) {
    this.width = width;
}
public int getHeight() {
    return height;
}
public void setHeight(int height) {
    this.height = height;
}
}
