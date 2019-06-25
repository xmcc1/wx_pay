package com.xmcc.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration /*表示该类为配置类*/
public class DruidConfig {
/*bean的名字 该方法的返回值作为bean交给spring容器管理 创建销毁方法*/
    @Bean(value = "druidDataSource",initMethod = "init",destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.druid")
    public DruidDataSource druidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setProxyFilters(Lists.newArrayList(statFilter()));
        return druidDataSource;
    }
    @Bean
    public StatFilter statFilter(){
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true);/*慢查询是否记录日志*/
        statFilter.setSlowSqlMillis(5);/*慢查询时间*/
        statFilter.setLogSlowSql(true);
        statFilter.setMergeSql(true);/*格式化sql*/
        return statFilter;
    }
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        /*Druid监控平台，输入 http://localhost:8080/druid即可访问平台*/
        return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
    }
}

