package org.example.spring.transaction.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.example.spring.transaction.config.RoutingDataSource.DatabaseType;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = JpaDataSourceConfig.BASE_PACKAGE)
public class JpaDataSourceConfig {

	public static final String BASE_PACKAGE = "org.example.spring.transaction";
	public static final String PERSIST_UNIT = "samplePersistUnit";

	@Bean
	public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		Map<String, String> jpaPropertyMap = this.jpaProperties().getProperties();
		Map<String, Object> hibernatePropertyMap =
			 this.hibernateProperties()
				  .determineHibernateProperties(jpaPropertyMap, new HibernateSettings());

		return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), jpaPropertyMap, null)
			 .dataSource(this.dataSource())
			 .properties(hibernatePropertyMap)
			 .persistenceUnit(PERSIST_UNIT)
			 .packages(BASE_PACKAGE)
			 .build();
	}

	@Bean
	public JpaProperties jpaProperties() {
		return new JpaProperties();
	}

	@Bean
	public HibernateProperties hibernateProperties() {
		return new HibernateProperties();
	}

	@Bean
	public DataSource dataSource() {
		return new LazyConnectionDataSourceProxy(this.routingDataSource());
	}

	@Bean
	public DataSource routingDataSource() {
		RoutingDataSource routingDataSource = new RoutingDataSource();

		routingDataSource.setDefaultTargetDataSource(this.readDataSource());
		routingDataSource.setTargetDataSources(new HashMap<>(){{
			put(DatabaseType.READ, readDataSource());
			put(DatabaseType.WRITE, writeDataSource());
		}});

		return routingDataSource;
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.write.hikari")
	public DataSource writeDataSource() {
		return new HikariDataSource();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.read.hikari")
	public DataSource readDataSource() {
		return new HikariDataSource();
	}
}
