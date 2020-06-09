package com.github.qcyin.util;


import com.github.qcyin.tuple.Tuple2;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author yqc
 * @date 2020/5/3 22:28
 */
@SuppressWarnings({"WeakerAccess", "SameParameterValue", "unused"})
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
        Objects.requireNonNull(collection, "collection must not be null");
        Objects.requireNonNull(delimiter, "delimiter must not be null");
        Objects.requireNonNull(prefix, "prefix must not be null");
        Objects.requireNonNull(suffix, "suffix must not be null");
        Objects.requireNonNull(consumer, "consumer must not be null");

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

    public static<T, K> Map<K, List<T>> groupingBy(Collection<T> collection, Function<T, K> classifier){
        return groupingBy(collection, classifier, CollectorHelper.toList());
    }


    /**
     * 分组
     * @param collection    需要分组的集合
     * @param classifier    分组依据
     * @param collector     收集器(提供收集 value 的容器, 集合元素和 value 的结合方式)
     * @param <T>           集合 element 类型
     * @param <K>           分组 key 类型
     * @param <R>           分组 value 类型
     * @return              分组结果
     */
    public static<T, K, R> Map<K, R> groupingBy(Collection<T> collection,
                                                   Function<T, K> classifier,
                                                   Tuple2<Supplier<R>, BiConsumer<R, T>> collector){
        Objects.requireNonNull(collection, "collection must not be null");
        Objects.requireNonNull(classifier, "classifier must not be null");
        Objects.requireNonNull(collector, "collector must not be null");
        Supplier<R> supplier = collector.getT1();
        BiConsumer<R, T> accumulator = collector.getT2();
        Objects.requireNonNull(supplier, "supplier must not be null");
        Objects.requireNonNull(accumulator, "accumulator must not be null");


        Map<K, R> map = new HashMap<>(16);
        for (T t : collection) {
            K key = Objects.requireNonNull(classifier.apply(t), "element cannot be mapped to a null key");
            R r = map.computeIfAbsent(key, k -> supplier.get());
            accumulator.accept(r, t);
        }
        return map;
    }


    /**
     * c - v ：c 减去与 v 相等的元素<br/>
     * example: remove(list, hashSet, (e, s)->s.contains(e))
     * @param c				被减数
     * @param v				减数
     * @param isEqual	    相等判别式
     * @param <T>			被减数元素类型
     * @param <V>			减数类型
     * @return				差
     */
    public static <T, V> Collection<T> remove(Collection<T> c, V v, BiFunction<T, V, Boolean> isEqual){
        if (org.springframework.util.CollectionUtils.isEmpty(c) || Objects.isNull(v)){
            return c;
        }
        List<T> list = new LinkedList<>();
        for (T t1 : c) {
            if (isEqual.apply(t1, v)) {
                continue;
            }
            list.add(t1);
        }
        return list;
    }


    /**
     * c1 - c2：c 减去 c2 含有的元素
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
        boolean isAdd;
        for (T1 t1 : c1) {
            isAdd = true;
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


    /*public static void main(String[] args) {

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

        System.out.println("==============================");

        List<String> strings = Arrays.asList("key", "key", "key", "val", "val", "val", "val");
        System.out.println(mkString(strings, ",", "[", "]"));

        System.out.println(strings.stream().collect(java.util.stream.Collectors.groupingBy(s -> s, java.util.stream.Collectors.counting())));

        Map<String, java.util.concurrent.atomic.AtomicLong> groupedMap = groupingBy(strings, s -> s, CollectorHelper.counting());

        System.out.println(groupedMap);

    }*/

}
