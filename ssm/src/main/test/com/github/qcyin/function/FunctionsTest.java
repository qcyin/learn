package com.github.qcyin.function;

import com.github.qcyin.tuple.Tuple2;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Supplier;

public class FunctionsTest {
    @Test
    public void caseWhen() throws Exception {
        Object defaultVal = 3;

        System.out.println(Functions.caseWhen(defaultVal, Functions.newTuple2(bTrue(), test()), Functions.newTuple2(bTrue(), test())));

        System.out.println("=======================");

        // Supplier lazy
        Supplier<Object> supplierDefaultVal = () -> 3;
        Supplier<Boolean> supplierTrue = () -> bTrue();
        Supplier<Boolean> supplierFalse = () -> bFalse();
        Supplier<Object> supplierObject = () -> test();
        Tuple2<Supplier<Boolean>, Supplier<Object>> t1 = new Tuple2<>(supplierTrue, supplierObject);
        Tuple2<Supplier<Boolean>, Supplier<Object>> t2 = new Tuple2<>(supplierTrue, supplierObject);
        System.out.println(caseWhen(supplierDefaultVal, t1, t2));

    }


    public Object test() {
        System.out.println("=====test=====");
        return 5;
    }

    public boolean bTrue(){
        System.out.println("=====bTrue=====");
        return true;
    }

    public boolean bFalse(){
        System.out.println("=====bFalse=====");
        return false;
    }

    @SafeVarargs
    public static Object caseWhen(Supplier<Object> defaultVal, Tuple2<Supplier<Boolean>, Supplier<Object>>... tuples) {
        if (tuples != null){
            for (Tuple2<Supplier<Boolean>, Supplier<Object>> tuple : tuples) {
                Optional<Tuple2<Supplier<Boolean>, Supplier<Object>>> optional = Optional.ofNullable(tuple);
                boolean boo = optional.map(Tuple2::getT1).map(Supplier::get).orElse(false);
                if (boo) {
                    return optional.map(Tuple2::getT2).map(Supplier::get).orElse(null);
                }
            }
        }
        return defaultVal.get();
    }

    @SafeVarargs
    public static Object switchCase(Object expression, Supplier<Object> defaultVal, Tuple2<Supplier<Object>, Supplier<Object>>... tuples) {
        if(tuples != null){
            for (Tuple2<Supplier<Object>, Supplier<Object>> tuple : tuples) {
                Optional<Tuple2<Supplier<Object>, Supplier<Object>>> optional = Optional.ofNullable(tuple);
                Object t1 = optional.map(Tuple2::getT1).map(Supplier::get).orElse(null);
                if (Functions.equals(expression, t1)) {
                    return optional.map(Tuple2::getT2).map(Supplier::get).orElse(null);
                }
            }
        }
        return defaultVal.get();
    }

}