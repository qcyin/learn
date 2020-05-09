package com.github.qcyin.util;


import com.github.qcyin.tuple.Tuple2;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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


    public static<T, K, R> Map<K, R> groupingBy(Collection<T> collection,
                                                   Function<T, K> classifier,
                                                   Tuple2<Supplier<R>, BiConsumer<R, T>> collector){
        Objects.requireNonNull(collection, "collection must not be null");
        Objects.requireNonNull(classifier, "classifier must not be null");
        Objects.requireNonNull(collector, "collector must not be null");
        Supplier<R> supplier = collector.getT1();
        BiConsumer<R, T> accumulator = collector.getT2();
        Objects.requireNonNull(classifier, "supplier must not be null");
        Objects.requireNonNull(collector, "accumulator must not be null");

        Map<K, R> map = new HashMap<>(16);
        for (T t : collection) {
            K key = Objects.requireNonNull(classifier.apply(t), "element cannot be mapped to a null key");
            R r = map.computeIfAbsent(key, k -> supplier.get());
            accumulator.accept(r, t);
        }
        return map;
    }



    public static void main(String[] args) {
        // test
        List<String> strings = Arrays.asList("key", "key", "key", "val", "val", "val", "val");
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






        System.out.println(strings.stream().collect(Collectors.groupingBy(s -> s, Collectors.counting())));


        Map<String, AtomicLong> groupedMap = groupingBy(strings, s -> s, CollectorHelper.counting());

        System.out.println(groupedMap);

    }



}
