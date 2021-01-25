package com.rt.orponing.dao.data;

@FunctionalInterface
public interface ICheckedConsumer<T> {
    void accept(T t) throws Exception;
}