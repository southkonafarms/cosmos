package com.zenred.cosmos.domain;

import java.util.Properties;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public abstract class AbstractJDBCDao {
	
	public static SimpleJdbcDaoSupport jdbcSetUp() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		// driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		/*
		 * driverManagerDataSource
		 * .setUrl("jdbc:mysql://sherpadb.matrix.cc/email_advert");
		 * driverManagerDataSource.setUsername("jredden");
		 * driverManagerDataSource.setPassword("nCXL3O2GVAwLBJnT");
		 */

		driverManagerDataSource
				.setUrl("jdbc:mysql://localhost/johntredden");
		driverManagerDataSource.setUsername("root");
		driverManagerDataSource
				.setPassword("N0kill!00");

		Properties connectionProperties = new Properties();
		connectionProperties.setProperty("noAccessToProcedureBodies", "true"); // *sigh*.
																				// to
																				// allow
																				// sp's
																				// to
																				// run
		connectionProperties.setProperty("allowPublicKeyRetrieval","true");
		connectionProperties.setProperty("useSSL","false");
		driverManagerDataSource.setConnectionProperties(connectionProperties);
		SimpleJdbcDaoSupport simpleJdbcDaoSupport = new SimpleJdbcDaoSupport();
		simpleJdbcDaoSupport.setDataSource(driverManagerDataSource);
		return simpleJdbcDaoSupport;
	}

	public static SimpleJdbcDaoSupport jdbcJohnTReddenSetUp() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		// driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		/*
		 * driverManagerDataSource
		 * .setUrl("jdbc:mysql://sherpadb.matrix.cc/email_advert");
		 * driverManagerDataSource.setUsername("jredden");
		 * driverManagerDataSource.setPassword("nCXL3O2GVAwLBJnT");
		 */

		driverManagerDataSource
				.setUrl("jdbc:mysql://localhost/johntredden");
		driverManagerDataSource.setUsername("root");
		driverManagerDataSource
				.setPassword("N0kill!00");

		Properties connectionProperties = new Properties();
		connectionProperties.setProperty("noAccessToProcedureBodies", "true"); // *sigh*.
		connectionProperties.setProperty("allowPublicKeyRetrieval","true");
		connectionProperties.setProperty("useSSL","false");
																		// to
																				// allow
																				// sp's
																				// to
																				// run
		driverManagerDataSource.setConnectionProperties(connectionProperties);
		SimpleJdbcDaoSupport simpleJdbcDaoSupport = new SimpleJdbcDaoSupport();
		simpleJdbcDaoSupport.setDataSource(driverManagerDataSource);
		return simpleJdbcDaoSupport;
	}

	public static SimpleJdbcInsert jdbcInsertSetup() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		// driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

		driverManagerDataSource
				.setUrl("jdbc:mysql://localhost/johntredden");
		driverManagerDataSource.setUsername("root");
		driverManagerDataSource
				.setPassword("N0kill!00");
				
		Properties connectionProperties = new Properties();
		connectionProperties.setProperty("allowPublicKeyRetrieval","true");
		connectionProperties.setProperty("useSSL","false");

		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(
				driverManagerDataSource);
		return simpleJdbcInsert;
	}

}
