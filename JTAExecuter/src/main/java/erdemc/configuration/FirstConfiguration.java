package erdemc.configuration;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import erdemc.dao.first.FirstDAO;
import erdemc.model.first.First;

@Configuration
@EnableJpaRepositories(basePackageClasses = FirstDAO.class, entityManagerFactoryRef = "firstEntityManager", transactionManagerRef = "firstTransactionManager")
public class FirstConfiguration {

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean firstEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(firstDataSource());
		em.setPackagesToScan(new String[] { First.class.getPackage().getName() });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(true);
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.hbm2ddl.auto", "none");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		em.setJpaPropertyMap(properties);

		return em;
	}

	@Primary
	@Bean
	public DataSource firstDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/jtatrial");
		dataSource.setUsername("erdemc");
		dataSource.setPassword("invader84;");

		return dataSource;
	}

	@Primary
	@Bean
	public PlatformTransactionManager firstTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(firstEntityManager().getObject());
		return transactionManager;
	}

}
