package com.study.shop.web.servlet;

import com.study.shop.util.ResourceUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AssetsServlet extends HttpServlet {
    ResourceUtil resourceUtil = new ResourceUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestURI = req.getRequestURI();

        ServletOutputStream outputStream = resp.getOutputStream();
        InputStream inputStream = resourceUtil.getResourceAsStream(requestURI.substring(1));

        try {
            copyData(inputStream, outputStream);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void copyData(InputStream in, OutputStream out) throws Exception {
        byte[] buffer = new byte[8 * 1024];
        int len;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
    }
}
