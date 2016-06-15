package com.dao.genericdao.base;

import com.dao.genericdao.config.DruidDataSourceConfig;
import com.dao.genericdao.config.JPAConfig;
import com.dao.genericdao.config.JPATransactionConfig;
import com.dao.genericdao.config.MybatisConfig;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource(value = {"classpath:application.properties"})
@Import(value = { DruidDataSourceConfig.class , JPATransactionConfig.class ,
        JPAConfig.class , MybatisConfig.class
})
@ComponentScan(basePackages="com.dao.genericdao" , useDefaultFilters=false)
public class BaseConfig {

}
