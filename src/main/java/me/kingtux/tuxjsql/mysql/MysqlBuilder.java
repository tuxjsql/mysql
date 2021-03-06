package me.kingtux.tuxjsql.mysql;

import me.kingtux.tuxjsql.basic.builders.BasicSQLBuilder;
import me.kingtux.tuxjsql.basic.sql.BasicDataTypes;
import me.kingtux.tuxjsql.basic.sql.select.BasicJoinStatement;
import me.kingtux.tuxjsql.core.Configuration;
import me.kingtux.tuxjsql.core.TuxJSQL;
import me.kingtux.tuxjsql.core.builders.ColumnBuilder;
import me.kingtux.tuxjsql.core.builders.TableBuilder;
import me.kingtux.tuxjsql.core.connection.ConnectionProvider;
import me.kingtux.tuxjsql.core.connection.ConnectionSettings;
import me.kingtux.tuxjsql.core.sql.DeleteStatement;
import me.kingtux.tuxjsql.core.sql.InsertStatement;
import me.kingtux.tuxjsql.core.sql.SQLDataType;
import me.kingtux.tuxjsql.core.sql.UpdateStatement;
import me.kingtux.tuxjsql.core.sql.select.JoinStatement;
import me.kingtux.tuxjsql.core.sql.select.SelectStatement;
import me.kingtux.tuxjsql.core.sql.where.SubWhereStatement;
import me.kingtux.tuxjsql.core.sql.where.WhereStatement;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Properties;

public final class MysqlBuilder extends BasicSQLBuilder {
    public static final String URL = "jdbc:mysql://%1$s/%2$s";
    public static final String JDBC_CLASS = "com.mysql.cj.jdbc.Driver";

    @Override
    public TableBuilder createTable() {
        return new MysqlTableBuilder(tuxJSQL);
    }

    @Override
    public ColumnBuilder createColumn() {
        return new MysqlColumnBuilder(tuxJSQL);
    }

    @Override
    public WhereStatement createWhere() {
        return new MysqlWhereStatement(tuxJSQL);
    }

    @Override
    public SubWhereStatement createSubWhereStatement() {
        return new MysqlSubWhereStatement(tuxJSQL);
    }

    @Override
    public <T> WhereStatement<T> createWhere(T t) {
        return new MysqlWhereStatement<>(t, tuxJSQL);
    }

    @Override
    public <T> SubWhereStatement<T> createSubWhereStatement(T t) {
        return new MysqlSubWhereStatement<>(t, tuxJSQL);
    }

    @Override
    public SelectStatement createSelectStatement() {
        return new MysqlSelectStatement(tuxJSQL);
    }

    @Override
    public JoinStatement createJoinStatement(SelectStatement basicSelectStatement) {
        return new BasicJoinStatement(basicSelectStatement);
    }

    @Override
    public UpdateStatement createUpdateStatement() {
        return new MysqlUpdateStatement(tuxJSQL);
    }

    @Override
    public DeleteStatement createDeleteStatement() {
        return new MysqlDeleteStatement(tuxJSQL);
    }

    @Override
    public String name() {
        return "Mysql";
    }

    @Override
    public String jdbcClass() {
        return JDBC_CLASS;
    }

    @Override
    public SQLDataType convertDataType(BasicDataTypes dataType) {
        for (MysqlDataTypes type : MysqlDataTypes.values()) {
            if (type.getTypes() == dataType) {
                return type;
            }
        }
        return null;
    }

    @Override
    public InsertStatement createInsertStatement() {
        return new MysqlInsertStatement(tuxJSQL);
    }

    @Override
    public void configureConnectionProvider(ConnectionProvider provider, Properties userProperties) throws Exception {
        String url = String.format(URL, userProperties.getProperty("db.host"), userProperties.getProperty("db.db"));
        if (userProperties.getProperty("url.other.options") != null) {
            url += "?" + userProperties.getProperty("url.other.options");
        }
        if (TuxJSQL.getLogger().isDebugEnabled())
            TuxJSQL.getLogger().debug(String.format("URL:%s", url));
        provider.setup(new ConnectionSettings(jdbcClass(), url), userProperties);
    }
    @Override
    public void configureConnectionProvider(ConnectionProvider provider, Configuration configuration) throws Exception {
        Pair<ConnectionSettings, Properties> connection = configuration.createConnection();
        provider.setup(connection.getLeft(), connection.getRight());
    }

    @Override
    public <T> ColumnBuilder<T> createColumn(T t) {
        return new MysqlColumnBuilder<>(tuxJSQL, t);
    }
}
