package me.yusuf.ecommerce_builder.demo.utils;

public interface Cached <K>{
    void invalidateCache(K key);
}
