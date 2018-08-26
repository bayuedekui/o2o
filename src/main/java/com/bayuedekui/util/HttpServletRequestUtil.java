package com.bayuedekui.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
    /**
     * 将请求传过来的key转化成int类型的数
     *
     * @param request
     * @param key
     * @return
     */
    public static int getInt(HttpServletRequest request, String key) {
        try {
            return Integer.parseInt(request.getParameter(key));     //或者用integer.decode();方法
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 将请求中的key转化为Long类型的数据
     * @param request
     * @param key
     * @return
     */
    public static long getLong(HttpServletRequest request,String key){
        try {
            return Long.parseLong(request.getParameter(key));
        }catch(Exception e){
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
    public static double getDouble(HttpServletRequest request, String key) {
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
    public static boolean getBoolean(HttpServletRequest request, String key) {
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
    public static String getString(HttpServletRequest request, String key) {
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
