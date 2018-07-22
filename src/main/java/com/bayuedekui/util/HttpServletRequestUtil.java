package com.bayuedekui.util;

public class HttpServletRequestUtil {
    /**
     * 将请求传过来的key转化成int类型的数
     *
     * @param request
     * @param key
     * @return
     */
    public static int getInt(javax.servlet.http.HttpServletRequest request, String key) {
        try {
            return Integer.parseInt(request.getParameter(key));     //或者用integer.decode();方法
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 转化为double类型的数据
     *
     * @param request
     * @param key
     * @return
     */
    public static double getDouble(javax.servlet.http.HttpServletRequest request, String key) {
        try {
            return Double.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 转化为boolean类型
     *
     * @param request
     * @param key
     * @return
     */
    public static boolean getBoolean(javax.servlet.http.HttpServletRequest request, String key) {
        try {
            return Boolean.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 返回字符宣传类型的
     * @param request
     * @param key
     * @return
     */
    public static String getString(javax.servlet.http.HttpServletRequest request, String key) {
        try {
            String result = request.getParameter(key);
            if (result != null) {
                return result.trim();
            }
            if("".equals(result)){
                return null;
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

}
