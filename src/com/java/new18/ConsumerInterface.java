package com.java.new18;

import org.apache.poi.ss.formula.functions.T;

public interface ConsumerInterface<T> {
    void accept(T t);
}
