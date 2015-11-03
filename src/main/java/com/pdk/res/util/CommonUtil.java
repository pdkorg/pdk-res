package com.pdk.res.util;

import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hubo on 2015/8/25
 */
public class CommonUtil {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static String formatDate() {
        return formatDate(new Date());
    }

    public static String getRealPath(String resource) {
        return ContextLoaderListener.getCurrentWebApplicationContext().getServletContext().getRealPath(resource);
    }

    public static String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    public static String getContextPath() {
        return ContextLoaderListener.getCurrentWebApplicationContext().getServletContext().getContextPath();
    }


}
