package org.example.spring.transaction.config;

import static org.example.spring.transaction.config.DataSourceConfig.BASE_PACKAGE;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = DataSourceConfig.BASE_PACKAGE)
public class DataSourceConfig {

	public static final String BASE_PACKAGE = "org.example.spring.transaction";
	public static final String PERSIST_UNIT = "samplePersistUnit";

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		Map<String, String> jpaPropertyMap = jpaProperties().getProperties();
		Map<String, Object> hibernatePropertyMap =
			 hibernateProperties().determineHibernateProperties(jpaPropertyMap, new HibernateSettings());

		return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), jpaPropertyMap, null)
			 .dataSource(integrationDataSource())
			 .properties(hibernatePropertyMap)
			 .persistenceUnit(PERSIST_UNIT)
			 .packages(BASE_PACKAGE)
			 .build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.write.hikari")
	public DataSource integrationWriteDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.read.hikari")
	public DataSource integrationReadDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public DataSource integrationRoutingDataSource() {
		RoutingDataSource routingDataSource = new RoutingDataSource();

		routingDataSource.setDefaultTargetDataSource(integrationReadDataSource());
		routingDataSource.setTargetDataSources(this.getIntegrationTargetDataSourceMap());

		return routingDataSource;
	}

	@Bean
	public DataSource integrationDataSource() {
		return new LazyConnectionDataSourceProxy(integrationRoutingDataSource());
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.jpa")
	public JpaProperties jpaProperties() {
		return new JpaProperties();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.jpa.hibernate")
	public HibernateProperties hibernateProperties() {
		return new HibernateProperties();
	}

	private Map<Object, Object> getIntegrationTargetDataSourceMap() {
		Map<Object, Object> targetDataSourceMap = new HashMap<>();

		targetDataSourceMap.put(
			 RoutingDataSource.DatabaseType.READ, integrationReadDataSource());
		targetDataSourceMap.put(
			 RoutingDataSource.DatabaseType.WRITE, integrationWriteDataSource());

		return targetDataSourceMap;
	}
}
