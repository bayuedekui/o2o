package com.bayuedekui.util;

public class PageCalculator {
    /**
     * 根据页码,返回数据库中查询需要的条数
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static int calculateRowIndex(int pageIndex, int pageSize) {
        return pageIndex > 0 ? (pageIndex - 1) * pageSize : 0;
    }
}
