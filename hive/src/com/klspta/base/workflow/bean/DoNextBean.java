package com.klspta.base.workflow.bean;

import java.io.InputStream;
import java.util.Set;

public class DoNextBean {
    private InputStream is = null;
    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;
    private Set<String> nextNames = null;
    
    private boolean isSuccess = true;
    
    public DoNextBean(InputStream is, int x, int y, int width, int height, Set<String> nextNames){
        this.is = is;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.nextNames = nextNames;
    }
    
    public DoNextBean(boolean boo){
        isSuccess = boo;
    }
    
    public Set<String> getNextNames(){
        return nextNames;
    }
    
    public boolean isSuccess() {
        return isSuccess;
    }
    
    public InputStream getImageAsInputStream() {
        return is;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

}
