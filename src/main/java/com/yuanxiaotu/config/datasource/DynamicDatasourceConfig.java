package com.yuanxiaotu.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class DynamicDatasourceConfig {

    @Primary
    @Bean("masterDataSource")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("masterDataSource")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean("masterSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/one/*.xml"));
        return bean.getObject();
    }

    @Primary
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }

}
