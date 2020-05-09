package com.github.qcyin.util;

import com.github.qcyin.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public final class CollectorHelper {
    private CollectorHelper(){}

    public static<T> Tuple2<Supplier<AtomicLong>, BiConsumer<AtomicLong, T>> counting(){
        return new Tuple2<>(AtomicLong::new, (i, t) -> i.incrementAndGet());
    }

    public static<T> Tuple2<Supplier<List<T>>, BiConsumer<List<T>, T>> toList(){
        return new Tuple2<>(ArrayList::new, List::add);
    }

}