package com.yuanxiaotu.config.datasource;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.yuanxiaotu.config.datasource.bean.DynamicDatasource;
import com.yuanxiaotu.config.datasource.enums.DataSourceEnum;
import com.yuanxiaotu.config.datasource.properties.DataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaotianyu
 */
@Slf4j(topic = "dynamic-datasource")
@EnableConfigurationProperties(value = {DataSourceProperties.class})
@Configuration
public class DynamicDatasourceConfig {


    private final DataSourceProperties properties;

    @Autowired
    public DynamicDatasourceConfig(DataSourceProperties properties) {
        this.properties = properties;
        log.info("properties :{}", JSON.toJSONString(properties));
    }

    @Bean("masterDataSource")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(properties.getMaster().getDriverClassName())
                .url(properties.getMaster().getUrl())
                .username(properties.getMaster().getUsername())
                .password(properties.getMaster().getPassword())
                .build();
    }

    @Bean("slaveDataSource")
    public DataSource slaveDataSource(){
        return DataSourceBuilder.create()
                .driverClassName(properties.getSlave().getDriverClassName())
                .url(properties.getSlave().getUrl())
                .username(properties.getSlave().getUsername())
                .password(properties.getSlave().getPassword())
                .build();
    }

    @Bean("dynamicDatasource")
    @Primary
    public DynamicDatasource dynamicDatasource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                               @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        DynamicDatasource dynamicDatasource = new DynamicDatasource();
        dynamicDatasource.setDefaultTargetDataSource(masterDataSource);
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DataSourceEnum.MASTER, masterDataSource);
        targetDataSources.put(DataSourceEnum.SLAVE, slaveDataSource);
        dynamicDatasource.setTargetDataSources(targetDataSources);
        return dynamicDatasource;
    }

    @Primary
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDatasource") DynamicDatasource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/one/*.xml"));
        return bean.getObject();
    }


    @Primary
    @Bean
    public SqlSessionTemplate masterSqlSessionTemplate(SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }


    @Bean
    public PlatformTransactionManager transactionManager(DynamicDatasource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
