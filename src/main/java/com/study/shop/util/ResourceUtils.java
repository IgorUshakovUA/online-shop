package com.study.shop.util;

import java.io.InputStream;

public class ResourceUtils {

    public static InputStream getResourceAsStream(String path) {
        ClassLoader classLoader = ResourceUtils.class.getClassLoader();
        return classLoader.getResourceAsStream(path);
    }
}
