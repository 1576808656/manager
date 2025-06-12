package com.login.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.login.config.datasource.DataSourceContextHolder;
import com.login.config.datasource.DataSourceTypeEnum;

@Aspect
@Component
public class DataSourceAspect {

    @Before("@annotation(com.login.config.datasource.MasterDataSource)")
    public void setMasterDataSource(JoinPoint joinPoint) {
        DataSourceContextHolder.setDataSourceType(DataSourceTypeEnum.MASTER);
    }

    @Before("@annotation(com.login.config.datasource.SlaveDataSource)")
    public void setSlaveDataSource(JoinPoint joinPoint) {
        DataSourceContextHolder.setDataSourceType(DataSourceTypeEnum.SLAVE);
    }

    @After("@annotation(com.login.config.datasource.MasterDataSource) || @annotation(com.login.config.datasource.SlaveDataSource)")
    public void clearDataSource(JoinPoint joinPoint) {
        DataSourceContextHolder.clearDataSourceType();
    }
}
