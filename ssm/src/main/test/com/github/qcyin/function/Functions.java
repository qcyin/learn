package com.github.qcyin.function;

import com.alibaba.fastjson.util.TypeUtils;
import com.github.qcyin.staticfinal.StaticFinalNumber;
import com.github.qcyin.tuple.Tuple2;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 参考mysql和oracle的function
 *
 * @author yqc
 * @since 2019-09-03 11:53
 */
public final class Functions {

    /**
     * see BigDecimal scale
     */
    private static final int scale = 9;
    private static final Map<String, Integer> DATE_PARAM = new HashMap<>();

    static {
        DATE_PARAM.put("yyyy", Calendar.YEAR);
        DATE_PARAM.put("MM", Calendar.MONTH);
        DATE_PARAM.put("dd", Calendar.DATE);
        DATE_PARAM.put("DD", Calendar.DATE);
        DATE_PARAM.put("HH", Calendar.HOUR_OF_DAY);
        DATE_PARAM.put("mm", Calendar.MINUTE);
        DATE_PARAM.put("mi", Calendar.MINUTE);
        DATE_PARAM.put("ss", Calendar.SECOND);
    }

    public static String TO_CHAR(Object o) {
        return TypeUtils.castToString(o);
    }

    /**
     * 提取日期中某个要素（年、月、日、时、分、秒）
     * TO_CHAR('2019-01-01 00:00:00',"yyyy") == "2019"
     *
     * @param time 日期
     * @param unit 要素名
     * @return 要素
     */
    public static String TO_CHAR(Object time, String unit) {
        if (time == null || !DATE_PARAM.containsKey(unit)) {
            return null;
        }
        if (time instanceof Date) {
            Calendar cal = Calendar.getInstance();
            cal.setTime((Date) time);
            int u = DATE_PARAM.get(unit);
            return String.valueOf(cal.get(u) + (u == Calendar.MONTH ? 1 : 0));
        } else {
            return TO_CHAR(time);
        }
    }

    public static Double TO_NUMBER(Object o) {
        return TypeUtils.castToDouble(o);
    }

    /**
     * 时间截取
     * TRUNC('2019-10-10 10:10:10',"yyyy") == '2019-01-01 00:00:00'
     * TRUNC('2019-10-10 10:10:10',"MM") == '2019-10-01 00:00:00'
     * TRUNC('2019-10-10 10:10:10',"dd") == '2019-10-10 00:00:00'
     * TRUNC('2019-10-10 10:10:10',"mm") == '2019-10-10 10:10:00'
     *
     * @param time 时间
     * @param unit 要素名
     * @return 时间
     */
    public static Date TRUNC(Object time, String unit) {

        Calendar cal = Calendar.getInstance();
        if (time instanceof Date) {
            cal.setTime((Date) time);
        } else {
            return null;
        }

        Integer param = DATE_PARAM.getOrDefault(unit, DATE_PARAM.get("dd"));
        if (param <= Calendar.YEAR) {
            cal.set(Calendar.MONTH, 0);
        }
        if (param <= Calendar.MONTH) {
            cal.set(Calendar.DATE, 1);
        }
        if (param <= Calendar.DATE) {
            cal.set(Calendar.HOUR_OF_DAY, 0);
        }
        if (param <= Calendar.HOUR_OF_DAY) {
            cal.set(Calendar.MINUTE, 0);
        }
        if (param <= Calendar.MINUTE) {
            cal.set(Calendar.SECOND, 0);
        }
        if (param <= Calendar.SECOND) {
            cal.set(Calendar.MILLISECOND, 0);
        }
        return cal.getTime();
    }

    public static Date TRUNC(Object time) {
        return TRUNC(time, "dd");
    }


    /**
     * 计算两个日期相差的天数
     */
    public static double apartDay(Object time1, Object time2) {
        if (time1 instanceof Date && time2 instanceof Date) {
            Date d1 = (Date) time1;
            Date d2 = (Date) time2;
            return BigDecimal.valueOf(d1.getTime() - d2.getTime())
                    .divide(BigDecimal.valueOf(StaticFinalNumber.DAY), scale, BigDecimal.ROUND_CEILING).doubleValue();
        }
        return 0.0;
    }

    public static Object NVL(Object o1, Object o2) {
        return (null != o1 ? o1 : o2);
    }

    /**
     * 字符串截取
     * 【数据库字符串截取与java字符串截取区别较大】
     *
     * @param s     字符串
     * @param begin 开始下标
     * @param end   结束下标
     * @return
     */
    public static String SUBSTR(Object s, Integer begin, Integer end) {
        if (s == null || begin == null || end == null) {
            return null;
        }
        if (!(s instanceof String || s instanceof Number) || begin == 0) {
            return "";
        }
        String str = s.toString();
        int len = str.length();

        int beginIndex = begin + (begin < 0 ? len : -1);
        if (beginIndex >= len || beginIndex < 0) {
            return "";
        }

        int endIndex = beginIndex + end;
        if (endIndex < beginIndex) {
            endIndex = beginIndex;
        } else if (endIndex > len) {
            endIndex = len;
        }
        return str.substring(beginIndex, endIndex);
    }


    public static String SUBSTR(int i, int begin, int end) {
        return SUBSTR(String.valueOf(i), begin, end);
    }

    public static String SUBSTR(char c, int begin, int end) {
        return SUBSTR(String.valueOf(c), begin, end);
    }

    public static String SUBSTR(long l, int begin, int end) {
        return SUBSTR(String.valueOf(l), begin, end);
    }

    public static String SUBSTR(float f, int begin, int end) {
        return SUBSTR(String.valueOf(f), begin, end);
    }

    public static String SUBSTR(double d, int begin, int end) {
        return SUBSTR(String.valueOf(d), begin, end);
    }


    /**
     * 字符串转时间(以'yyyyMMdd hh:mm:ss.SSS'格式处理)
     *
     * @param time   字符串
     * @param format 格式
     * @return 时间
     */
    public static Date TO_TIMESTAMP(String time, String format) {
        try {
            return new SimpleDateFormat("yyyyMMdd hh:mm:ss.SSS").parse(time);
        } catch (ParseException e) {
            //弱化ParseException
            throw new RuntimeException("TO_TIMESTAMP error!!! time=【" + time + "】" + "format=【" + format + "】");
        }
    }

    /**
     * 时间增加
     *
     * @param time      时间
     * @param increment 增加量（可以为负数）
     * @param format    单位
     * @return
     */
    public static Date ADD_DATE(Object time, int increment, String format) {
        Calendar cal = Calendar.getInstance();
        if (time instanceof Date) {
            cal.setTime((Date) time);
        } else {
            return null;
        }

        if ("year".equals(format)) {
            cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + increment);
        } else if ("month".equals(format)) {
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + increment);
        } else if ("day".equals(format)) {
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) + increment);
        } else if ("hour".equals(format)) {
            cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + increment);
        } else if ("minute".equals(format)) {
            cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + increment);
        } else if ("second".equals(format)) {
            cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + increment);
        } else if ("millisecond".equals(format)) {
            cal.set(Calendar.MILLISECOND, cal.get(Calendar.MILLISECOND) + increment);
        } else {
            return null;
        }

        return cal.getTime();
    }

    /**
     * 替换case (when ... then ...)+ (else ...)? end语法
     */
    @SafeVarargs
    public static Object caseWhen(Object defaultVal, Tuple2<Boolean, Object>... tuples) {
        if(tuples == null){
            return defaultVal;
        }

        for (Tuple2<Boolean, Object> tuple : tuples) {
            if (tuple != null) {
                Boolean t1 = tuple.getT1();
                if (t1 != null && t1) {
                    return tuple.getT2();
                }
            }
        }
        return defaultVal;
    }

    /**
     * 替换case ... (when ... then ...)+ (else ...)? end语法
     */
    @SafeVarargs
    public static Object switchFunc(Object expression, Object defaultVal, Tuple2<Object, Object>... tuples) {
        if(tuples == null){
            return defaultVal;
        }

        for (Tuple2<Object, Object> tuple : tuples) {
            if (null != tuple && equals(expression, tuple.getT1())) {
                return tuple.getT2();
            }
        }
        return defaultVal;
    }

    public static String TRIM(Object str) {
        return null == str ? "" : str.toString().trim();
    }

    public static String RTRIM(Object str) {
        return null == str ? "" : str.toString().replaceAll("\\s+$", "");
    }

    public static String LTRIM(Object str) {
        return null == str ? "" : str.toString().replaceAll("^\\s+", "");
    }

    public static String REPLACE(Object str, String old, String now) {
        return (old == null || now == null) ? "" : (null == str ? "" : str.toString().replace(old, now));
    }


    public static boolean In(Object o, Object... arr) {

        if (arr == null) {
            return o == null;
        }

        //只有一个时，判断是否为一个集合
        if (arr.length == 1 && arr[0] instanceof Collection) {
            return In(o, (Collection) arr[0]);
        }
        for (Object anArr : arr) {
            if (Objects.equals(o, anArr)) {
                return true;
            }
        }
        return false;
    }

    public static boolean In(Object o, Collection arr) {
        return null != arr && arr.contains(o);
    }


    public static Tuple2<Boolean, Object> newTuple2(Boolean b, Object o) {
        return new Tuple2<>(null == b ? false : b, o);
    }


    public static boolean NotIn(Object o, Object... arr) {
        return !In(o, arr);
    }

    public static boolean EXISTS(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof String) {
            String s = (String) o;
            return !s.isEmpty();
        }
        if (o instanceof Collection) {
            Collection c = (Collection) o;
            return !c.isEmpty();
        }
        return true;
    }


    public static boolean SYS_EXISTS(Object o) {
        //特殊处理导致
        return EXISTS(o);
    }


    public static boolean betweenAnd(Object obj, Object lowerBound, Object upperBound) {
        try {
            BigDecimal o = TypeUtils.castToBigDecimal(obj);
            BigDecimal l = TypeUtils.castToBigDecimal(lowerBound);
            BigDecimal u = TypeUtils.castToBigDecimal(upperBound);
            if (null == o) {
                return null == u || null == l;
            }
            if (null == u || null == l) {
                return false;
            }
            return o.compareTo(l) >= 0 && o.compareTo(u) <= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean notBetweenAnd(Object obj, Object lowerBound, Object upperBound) {
        try {
            BigDecimal o = TypeUtils.castToBigDecimal(obj);
            BigDecimal l = TypeUtils.castToBigDecimal(lowerBound);
            BigDecimal u = TypeUtils.castToBigDecimal(upperBound);
            if (null == o || null == upperBound || null == lowerBound) {
                return false;
            }
            //两边不取等
            return o.compareTo(l) > 0 && o.compareTo(u) < 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //==============三角函数====================
    public static double SIN(Object o) {
        return Math.sin(TypeUtils.castToDouble(o));
    }

    public static double COS(Object o) {
        return Math.cos(TypeUtils.castToDouble(o));
    }

    public static double ASIN(Object o) {
        return Math.asin(TypeUtils.castToDouble(o));
    }

    public static double ACOS(Object o) {
        return Math.acos(TypeUtils.castToDouble(o));
    }

    public static double TAN(Object o) {
        return Math.tan(TypeUtils.castToDouble(o));
    }

    public static double TANH(Object o) {
        return Math.tanh(TypeUtils.castToDouble(o));
    }
    //==================================


    public static double POWER(Object a, Object b) {
        Double d1 = TypeUtils.castToDouble(a);
        Double d2 = TypeUtils.castToDouble(b);
        return (d2 == null || d2 == 0) ? 1.0 : (d1 == null) ? 0 : Math.pow(d1, d2);
    }

    public static double SQRT(Object a) {
        Double d = TypeUtils.castToDouble(a);
        return d == null ? 0.0 : Math.sqrt(d);
    }

    public static double FLOOR(Object a) {
        Double d = TypeUtils.castToDouble(a);
        return d == null ? 0.0 : Math.floor(d);
    }

    /**
     * 获取当前系统时间
     */
    public static Date SYSDATE() {
        return new Date();
    }


    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean XOR(Object a, Object b) {
        Boolean booleanA = Boolean.valueOf(a == null ? null : a.toString());
        Boolean booleanB = Boolean.valueOf(b == null ? null : b.toString());
        return Boolean.logicalXor(booleanA, booleanB);
    }

    public static boolean equals(Object a, Object b) {
        if (a == null && b == null) {
            //两个都为null
            return true;
        } else if (a == null || b == null) {
            //两个不同时为null
            return false;
        }

        //两个都不为null
        if (a instanceof Number || b instanceof Number) {
            try {
                //两个中一个是Number类型
                BigDecimal a1 = new BigDecimal(a.toString());
                BigDecimal b1 = new BigDecimal(b.toString());
                //两个相减与0作比较
                return a1.subtract(b1).doubleValue() == 0.0;
            } catch (NumberFormatException e) {

                //其中一个不可以转换为数字
                return false;
            }
        }
        return Objects.equals(a, b);
    }

    public static double ABS(double param) {
        return Math.abs(param);
    }


    public int LENGTH(Object o){
        if(o == null){
            return 0;
        }
        return o.toString().length();
    }
}
