package com.popsun.reflection.object.impl;

import com.popsun.reflection.object.Subject;
import com.popsun.reflection.util.Log;

/**
 * @author 吴志祥
 * @create 2020-03-06 11:22
 */
public class RealSubject implements Subject {
    private String data;

    public RealSubject() {
    }

    public RealSubject(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public void sayHello() {
        Log.info("Real subject say hello to everyone!");
    }

    @Override
    public void rent() {
        Log.info("Real subject will rent this building!");
    }
}
