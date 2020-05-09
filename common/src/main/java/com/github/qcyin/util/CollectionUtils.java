package com.github.qcyin.util;


import java.util.*;
import java.util.function.BiConsumer;

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
