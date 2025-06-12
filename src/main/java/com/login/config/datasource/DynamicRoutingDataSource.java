package com.login.config.datasource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Value("${DB_RW_SEPARATE_SWITCH:false}")
    private boolean dbRwSeparateSwitch;
    @Override
    protected Object determineCurrentLookupKey() {
        if(dbRwSeparateSwitch && DataSourceTypeEnum.SLAVE.equals(DataSourceContextHolder.getDataSourceType())) {
            System.out.println("DynamicRoutingDataSource 切换数据源到从库");
            return DataSourceTypeEnum.SLAVE;
        }
        System.out.println("DynamicRoutingDataSource 切换数据源到主库");
        // 根据需要指定当前使用的数据源，这里可以使用ThreadLocal或其他方式来决定使用主库还是从库
        return DataSourceTypeEnum.MASTER;
    }
}
