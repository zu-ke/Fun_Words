package com.zk.utils;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {

    /*定义一个文件上传的路径常量*/
    public static final String FURN_IMG_DIRECTORY = "assets/images/product-image";

    /*判断请求是不是ajax请求*/
    public static boolean isAjaxRequest(HttpServletRequest request){
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

}
