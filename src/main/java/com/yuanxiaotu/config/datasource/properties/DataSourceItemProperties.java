package com.yuanxiaotu.config.datasource.properties;

import lombok.Data;

/**
 * @author xiaotianyu
 */
@Data
public class DataSourceItemProperties {
    private String driverClassName;

    private String username;

    private String password;

    private String url;
}
