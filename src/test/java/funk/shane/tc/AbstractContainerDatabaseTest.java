package funk.shane.tc;

import static org.mockito.ArgumentMatchers.contains;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.junit.After;
import org.testcontainers.containers.JdbcDatabaseContainer;

/**
 * Borrowed from
 * https://github.com/testcontainers/testcontainers-java/blob/master/modules/jdbc-test/src/test/java/org/testcontainers/junit/AbstractContainerDatabaseTest.java
 * 
 * while I learn how to do these
 */
public abstract class AbstractContainerDatabaseTest {
    private final Set<HikariDataSource> datasourcesCleanup = new HashSet<>();

    public ResultSet query(final JdbcDatabaseContainer container, String sql) throws SQLException {
        DataSource ds = getDS(container);
        Statement stmt = ds.getConnection().createStatement();
        stmt.executeQuery(sql);

        ResultSet rs = stmt.getResultSet();
        rs.next();

        return rs;
    }

    public DataSource getDS(final JdbcDatabaseContainer container) {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(container.getJdbcUrl());
        config.setUsername(container.getUsername());
        config.setPassword(container.getPassword());
        config.setDriverClassName(container.getDriverClassName());

        final HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }

    @After
    public void tearDown() {
        datasourcesCleanup.forEach(HikariDataSource::close);
    }
}