package com.company;

public class StringData {

    final private RealPoint point;

    final private String str;

    public StringData(RealPoint rp, String str) {
        this.point = rp;
        this.str = str;
    }

    public RealPoint getPoint() {
        return point;
    }

    public String getStr() {
        return str;
    }
}
