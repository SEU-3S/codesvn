package com.klspta.base.wkt;

import java.text.DecimalFormat;

public class Point {
    private double x = 0;

    private double y = 0;

    DecimalFormat a = new DecimalFormat("#.0000000000");

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(String x, String y) {
        try {
            this.x = Double.parseDouble(x);
            this.y = Double.parseDouble(y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPointXY(String x, String y) {
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
    }

    public void setPointXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getX4Str() {
        return a.format(x);
    }

    public String getY4Str() {
        return a.format(y);
    }

    /**
     * 返回坐标点,x和y之间用空格分割
     * @return
     */
    public String getPoint() {
        return a.format(x) + " " + y;
    }

    /**
     * 
     * <br>Description:返回带，的x,y形式
     * <br>Author:陈强峰
     * <br>Date:2012-8-23
     * @return
     */
    public String getSplitePoint() {
        return a.format(x) + "," + y;
    }

    /**
     * 返回wkt格式的点
     * @return
     */
    public String toWKT() {
        return "POINT(" + getPoint() + ")";
    }
}
