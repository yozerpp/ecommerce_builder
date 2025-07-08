package me.yusuf.ecommerce_builder.shared.components;

import lombok.Getter;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataSourceHolder {
	private final Map<Integer, DataSource> datasourceMap = new HashMap<>();
	private final DataSource defaultDataSource;
	private final String datasourceUrl;
	public DataSourceHolder(DataSource defaultDataSource, String datasourceUrl) {
		this.defaultDataSource = defaultDataSource;
		this.datasourceUrl = datasourceUrl;
	}
	public DataSourceHolder(String datasourceUrl){
		this.datasourceUrl = datasourceUrl;
		this.defaultDataSource = createDefaultDatasource(datasourceUrl);
	}
	public DataSource get(int editorId) {
		DataSource dataSource;
		 if ((dataSource =datasourceMap.get(editorId))==null){
			dataSource = createDataSource(editorId);
		 }
		 return dataSource;
	}
	private DataSource createDataSource(int editorId) {
		boolean exists = false;
		try (var con = defaultDataSource.getConnection()) {
            con.createStatement().execute("CREATE SCHEMA demo" + editorId);
		} catch (SQLException e) {
			if (e.getSQLState().equals("42P06")) exists = true;
			else throw new RuntimeException(e);
		}
		var builder = DataSourceBuilder.create();
		var ret = builder.url( datasourceUrl + "?currentSchema=demo" + editorId)
				.driverClassName("org.postgresql.Driver")
				.username(datasourceUsername).password(datasourcePassword).build();
//		if (!exists)
//			try (Connection conn = ret.getConnection()) {
//				disableForeignKeyChecks(conn);
//				conn.createStatement().execute("DO $$\n" +
//						"DECLARE\n" +
//						"    source_schema text := 'public';\n" +
//						"    target_schema text := 'demo"+editorId+"';\n" +
//						"    tbl record;\n" +
//						"    sql text;\n" +
//						"BEGIN\n" +
//						"    -- First, copy table structures\n" +
//						"    FOR tbl IN\n" +
//						"        SELECT table_name\n" +
//						"        FROM information_schema.tables\n" +
//						"        WHERE table_schema = source_schema\n" +
//						"          AND table_type = 'BASE TABLE'\n" +
//						"    LOOP\n" +
//						"        -- Create table with same structure\n" +
//						"        sql := format(\n" +
//						"            'CREATE TABLE %I.%I (LIKE %I.%I INCLUDING ALL)',\n" +
//						"            target_schema, tbl.table_name,\n" +
//						"            source_schema, tbl.table_name\n" +
//						"        );\n" +
//						"        RAISE NOTICE 'Creating table: %', sql;\n" +
//						"        EXECUTE sql;\n" +
//						"    END LOOP;\n" +
//						"END\n" +
//						"$$;");
//				enableForeignKeyChecks(conn);
//			} catch (SQLException e) {
//				throw new RuntimeException(e);
//			}
		return ret;
    }
	private static DataSource createDefaultDatasource(String datasourceUrl){
		return DataSourceBuilder.create().driverClassName("org.postgresql.Driver").url(datasourceUrl).password(datasourcePassword).username(datasourceUsername).build();
	}
	/**
	 * Temporarily disable foreign key checks for data import operations
	 */
	private void disableForeignKeyChecks(Connection connection) throws SQLException {
		// Disable all foreign key constraints in the target schema
		connection.createStatement().execute(
			"DO $$ " +
			"DECLARE " +
			"    r RECORD; " +
			"BEGIN " +
			"    FOR r IN (SELECT conname, conrelid::regclass AS table_name " +
			"              FROM pg_constraint " +
			"              WHERE contype = 'f' " +
			"              AND connamespace = (SELECT oid FROM pg_namespace WHERE nspname = current_schema())) " +
			"    LOOP " +
			"        EXECUTE 'ALTER TABLE ' || r.table_name || ' DISABLE TRIGGER ALL'; " +
			"    END LOOP; " +
			"END $$;"
		);
	}
	
	/**
	 * Re-enable foreign key checks after data import operations
	 */
	private void enableForeignKeyChecks(Connection connection) throws SQLException {
		// Re-enable all foreign key constraints in the target schema
		connection.createStatement().execute(
			"DO $$ " +
			"DECLARE " +
			"    r RECORD; " +
			"BEGIN " +
			"    FOR r IN (SELECT conname, conrelid::regclass AS table_name " +
			"              FROM pg_constraint " +
			"              WHERE contype = 'f' " +
			"              AND connamespace = (SELECT oid FROM pg_namespace WHERE nspname = current_schema())) " +
			"    LOOP " +
			"        EXECUTE 'ALTER TABLE ' || r.table_name || ' ENABLE TRIGGER ALL'; " +
			"    END LOOP; " +
			"END $$;"
		);
	}
	
	@Getter
	private static final String datasourceUsername;
	@Getter
	private static final String datasourcePassword;
	static {
		var username = System.getenv("DATASOURCE_USERNAME");
		datasourceUsername = username!=null?username:"tipil";
		var password = System.getenv("DATASOURCE_PASSWORD");
		datasourcePassword = password!=null?password:"12345";
	}
}
