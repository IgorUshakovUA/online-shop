package com.study.shop.web;

import com.study.shop.util.ResourceUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AssetsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestURI = req.getRequestURI();

        ServletOutputStream outputStream = resp.getOutputStream();

        Path path = ResourceUtils.getFileFromResources(requestURI.substring(1)).toPath();
        Files.copy(path, outputStream);

    }
}
