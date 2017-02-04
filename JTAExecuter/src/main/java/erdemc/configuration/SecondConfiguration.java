package erdemc.configuration;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

import erdemc.dao.second.SecondDAO;
import erdemc.model.second.Second;

@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackageClasses = SecondDAO.class, entityManagerFactoryRef = "secondEntityManager", transactionManagerRef = "secondTransactionManager")
public class SecondConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean secondEntityManager(JpaVendorAdapter vendorAdapter) {
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");
		
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setJtaDataSource(secondDataSource());
		entityManager.setJpaVendorAdapter(vendorAdapter);
		entityManager.setPackagesToScan(Second.class.getPackage().getName());
		entityManager.setPersistenceUnitName("secondPersistenceUnit");
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}

	@Bean(initMethod = "init", destroyMethod = "close")
	@DependsOn("transactionManager")
	public DataSource secondDataSource() {
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl("jdbc:mysql://localhost:3306/jtrial_second");
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setPassword("invader84;");
		mysqlXaDataSource.setUser("erdemc");
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("xads2");
		return xaDataSource;
	}

//	@Bean
//	public PlatformTransactionManager secondTransactionManager() {
//		JpaTransactionManager transactionManager = new JpaTransactionManager();
//		transactionManager.setEntityManagerFactory(secondEntityManager().getObject());
//		return transactionManager;
//	}

}
