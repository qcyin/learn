package com.github.qcyin.tuple;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yqc
 * @date 2020/5/9 23:20
 */
@Data
@AllArgsConstructor
public class Tuple2<T1, T2> implements Serializable {
    private T1 t1;
    private T2 t2;


    @Override
    public String toString() {
        return "(" + t1 + "," + t2 + ")";
    }
}
