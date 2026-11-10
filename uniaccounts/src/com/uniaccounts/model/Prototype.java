package com.uniaccounts.model;

/**
 * Prototype pattern: Prototype interface (الواجهة التي تفرض وجود عملية النسخ).
 */
public interface Prototype<T> {
    T copy();
}