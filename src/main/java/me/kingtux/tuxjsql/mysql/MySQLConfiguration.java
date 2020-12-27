package me.kingtux.tuxjsql.mysql;

import me.kingtux.tuxjsql.core.Configuration;
import me.kingtux.tuxjsql.core.builders.SQLBuilder;
import me.kingtux.tuxjsql.core.connection.ConnectionSettings;
import me.kingtux.tuxjsql.core.tools.SimpleSupplier;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Properties;

public class MySQLConfiguration implements Configuration<MySQLConfiguration> {
    private Properties userProperties = new Properties();
    private int poolSize;

    @Override
    public Pair<ConnectionSettings, Properties> createConnection() {
        return null;
    }


    @Override
    public Properties getUserProperties() {
        return userProperties;
    }

    @Override
    public MySQLConfiguration setUserProperties(Properties properties) {
        this.userProperties = properties;
        return this;
    }

    @Override
    public MySQLConfiguration loadFromProperties(Properties properties) {
        this.userProperties = properties;
        return this;
    }

    @Override
    public MySQLConfiguration setThreadPoolSize(int i) {
        poolSize = i;
        return this;
    }

    @Override
    public SimpleSupplier<SQLBuilder> getSQLBuilder() {
        return MysqlBuilder::new;
    }

    @Override
    public int getThreadPoolSize() {
        return poolSize;
    }
}
