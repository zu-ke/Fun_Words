package com.zk.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

public class DataUtils {

    public static Integer parseInt(String val, Integer defaultVal) {

        try {
            return Integer.parseInt(val);
        }catch (Exception e) {
            System.out.println("数据转换异常");
        }
        return defaultVal;

    }

    public static <T> T copyParamToBean(Map value, T bean){
        try {
            BeanUtils.populate(bean, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bean;
    }

}
