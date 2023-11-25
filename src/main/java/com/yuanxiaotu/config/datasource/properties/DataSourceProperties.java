package com.yuanxiaotu.config.datasource.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiaotianyu
 */
@Data
@ConfigurationProperties("spring.datasource")
public class DataSourceProperties {
    private DataSourceItemProperties master;

    private DataSourceItemProperties slave;

}


