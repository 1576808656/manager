package com.login.config.datasource;

public class DataSourceContextHolder {
    private static final ThreadLocal<DataSourceTypeEnum> contextHolder = new ThreadLocal<>();

    public static void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static DataSourceTypeEnum getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}
