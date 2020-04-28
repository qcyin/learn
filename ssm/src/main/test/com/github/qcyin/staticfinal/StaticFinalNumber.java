package com.github.qcyin.staticfinal;

/**
 * Number常量类
 * Created by yqc on 2019/5/12.
 */
public final class StaticFinalNumber {

    private StaticFinalNumber() {
    }

    public static final long MILLISECOND = 1L;

    public static final long SECOND = MILLISECOND * 1000;

    public static final long MINUTE = SECOND * 60;

    public static final long HOUR = MINUTE * 60;

    public static final long DAY = HOUR * 24;

    public static final long WEEK = DAY * 7;
}
