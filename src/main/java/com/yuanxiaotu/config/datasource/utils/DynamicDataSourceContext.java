package com.yuanxiaotu.config.datasource.utils;

import com.yuanxiaotu.config.datasource.enums.DataSourceEnum;

/**
 * @author xiaotianyu
 */
public class DynamicDataSourceContext {
    private static final ThreadLocal<DataSourceEnum> DATASOURCE = new ThreadLocal<>();

    public static void set(DataSourceEnum dataSourceEnum){
        DATASOURCE.set(dataSourceEnum);
    }
    public static DataSourceEnum get(){
        return DATASOURCE.get();
    }

    public static void remove(){
        DATASOURCE.remove();
    }

}
