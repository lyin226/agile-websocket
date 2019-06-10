package com.agile.websocket.common.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author liuyi
 * @date 2019/5/27
 */
public class RandomStrUtils {


    /**
     * 获取指定位数的字符串
     * @param num
     * @return
     */
    public static String getRandomStr(int num) {
        return RandomStringUtils.randomAlphanumeric(num);
    }

}
