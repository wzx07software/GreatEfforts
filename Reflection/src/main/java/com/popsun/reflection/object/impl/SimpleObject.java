package com.popsun.reflection.object.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 吴志祥
 * @create 2020-03-05 15:10
 */
@Data
@AllArgsConstructor
public class SimpleObject {
    public String roller;
    public static String roller1;
    public final String roller2="3";
    private String name;
    private String code;

    public SimpleObject() {
    }

    SimpleObject(String name) {
        this.name = name;
    }

    protected SimpleObject(Integer name) {
        this.name = name.toString();
    }

    private SimpleObject(Float name) {
        this.name = name.toString();
    }

    public SimpleObject(String name, Integer... ints) {
        this.name = name;
        this.code = "" + ints.length;
    }

    @Override
    public String toString() {
        return name + " - " + code + " - " + roller;
    }

    private String hello() {
        return "Hello beauty!";
    }
}
