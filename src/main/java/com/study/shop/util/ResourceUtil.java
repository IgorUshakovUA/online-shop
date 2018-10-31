package com.study.shop.util;

import java.io.InputStream;

public class ResourceUtil {
    public static InputStream getResourceAsStream(String path) {
        ClassLoader classLoader = ResourceUtil.class.getClassLoader();
        return classLoader.getResourceAsStream(path);
    }
}
