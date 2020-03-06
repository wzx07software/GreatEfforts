package com.popsun.reflection.object.impl;

import com.popsun.reflection.object.ComplexObject;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴志祥
 * @create 2020-03-05 15:02
 */
@Data
public class ComplexObjectImpl implements ComplexObject, Comparable<ComplexObjectImpl>, Serializable {
    private int data;

    public int compareTo(ComplexObjectImpl o) {
        return data - o.getData();
    }
}
