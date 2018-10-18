package com.study.shop.util;

import java.io.File;

public class ResourceUtils {
    public static File getFileFromResources(String path) {
        ClassLoader classLoader = ResourceUtils.class.getClassLoader();
        return new File(classLoader.getResource(path).getFile());

    }

    public static File getResourceDirectory() {
        ClassLoader classLoader = ResourceUtils.class.getClassLoader();
        return new File(classLoader.getResource(".").getFile());

    }
}
