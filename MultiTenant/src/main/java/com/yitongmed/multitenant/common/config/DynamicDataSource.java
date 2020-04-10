package com.yitongmed.multitenant.common.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final InheritableThreadLocal<String> dataSourceKey = new InheritableThreadLocal();

    public static void clear() {
        DynamicDataSource.dataSourceKey.remove();
    }

    public static void setDataSourceKey(String key) {
        DynamicDataSource.dataSourceKey.set(key);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSource.dataSourceKey.get();
    }
}
