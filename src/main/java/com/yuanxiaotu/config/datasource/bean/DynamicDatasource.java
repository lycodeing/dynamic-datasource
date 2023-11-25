package com.yuanxiaotu.config.datasource.bean;

import com.yuanxiaotu.config.datasource.utils.DynamicDataSourceContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDatasource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContext.get();
    }
}
