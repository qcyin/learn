package com.github.qcyin.util;


import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * @author yqc
 * @date 2020/5/322:28
 */
public final class CollectionUtils {
    private CollectionUtils(){}

    public static<T> String mkString(Collection<T> collection) {
        return mkString(collection, "");
    }

    public static<T> String mkString(Collection<T> collection, CharSequence delimiter) {
        return mkString(collection, delimiter, "", "");
    }

    public static<T> String mkString(Collection<T> collection, CharSequence delimiter, BiConsumer<StringBuilder, T> consumer) {
        return mkString(collection, delimiter, "", "", consumer);
    }

    public static<T> String mkString(Collection<T> collection, CharSequence delimiter, CharSequence prefix, CharSequence suffix){
        return mkString(collection, delimiter, prefix, suffix,  StringBuilder::append);
    }


    public static<T> String mkString(Collection<T> collection, CharSequence delimiter, CharSequence prefix, CharSequence suffix, BiConsumer<StringBuilder, T> consumer) {
        Objects.requireNonNull(collection, "collection must not br null");
        Objects.requireNonNull(delimiter, "delimiter must not br null");
        Objects.requireNonNull(prefix, "prefix must not br null");
        Objects.requireNonNull(suffix, "suffix must not br null");
        Objects.requireNonNull(consumer, "consumer must not br null");

        StringBuilder sbd = new StringBuilder(prefix);
        Iterator<T> iterator = collection.iterator();
        boolean hasNext = iterator.hasNext();
        while (hasNext){
            consumer.accept(sbd, iterator.next());
            if (hasNext = iterator.hasNext()) {
                sbd.append(delimiter);
            }
        }
        return sbd.append(suffix).toString();
    }

    /**
     * c - v <br/>
     * example: remove(list, hashSet, (e, s)->s.contains(e))
     * @param c				被减数
     * @param v				减数
     * @param isAnyEqual	相等判别式
     * @param <T>			被减数元素类型
     * @param <V>			减数类型
     * @return				差
     */
    public static <T, V> Collection<T> remove(Collection<T> c, V v, BiFunction<T, V, Boolean> isAnyEqual){
        if (org.springframework.util.CollectionUtils.isEmpty(c) || Objects.isNull(v)){
            return c;
        }
        List<T> list = new LinkedList<>();
        for (T t1 : c) {
            if (isAnyEqual.apply(t1, v)) {
                continue;
            }
            list.add(t1);
        }
        return list;
    }


    /**
     * c1 - c2
     * @param c1			被减数
     * @param c2			减数
     * @param isEqual		相等判别式
     * @param <T1>			被减数元素类型
     * @param <T2>			减数元素类型
     * @return				差
     */
    public static <T1, T2> List<T1> removeAll(List<T1> c1, List<T2> c2, BiFunction<T1, T2, Boolean> isEqual){
        if (org.springframework.util.CollectionUtils.isEmpty(c1) || org.springframework.util.CollectionUtils.isEmpty(c2)){
            return c1;
        }
        List<T1> list = new LinkedList<>();
        for (T1 t1 : c1) {
            boolean isAdd = true;
            for (T2 t2 : c2) {
                if (isEqual.apply(t1, t2)) {
                    isAdd = false;
                    break;
                }
            }
            if (isAdd) {
                list.add(t1);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        // test
        List<String> strings = Arrays.asList("1", "2", "3");
        System.out.println(mkString(strings, ",", "[", "]"));

        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>(16);
        map.put("k", "key");
        map.put("v", "value");

        Map<String, Object> map2 = new HashMap<>(16);
        map2.put("k", "key");
        map2.put("v", "value");

        mapList.add(map);
        mapList.add(map2);

        System.out.println(mkString(mapList, ","));

        System.out.println(mkString(mapList, ",", (sbd, kv) -> {
            sbd.append(mkString(kv.entrySet(),", ", "{", "}"));
        } ));

        System.out.println(mkString(mapList, ",", (sbd, kv) -> sbd.append(kv.get("k")).append(":").append(kv.get("v")) ));



    }

}
