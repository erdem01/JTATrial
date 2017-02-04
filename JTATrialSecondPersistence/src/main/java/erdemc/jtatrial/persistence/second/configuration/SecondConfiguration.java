package erdemc.jtatrial.persistence.second.configuration;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

import erdemc.jtatrial.persistence.second.annotations.StandaloneProfile;
import erdemc.jtatrial.persistence.second.dao.SecondDAO;
import erdemc.jtatrial.persistence.second.model.Second;
import erdemc.jtatrial.txn.configuration.AtomikosJtaPlatform;
import erdemc.jtatrial.txn.configuration.JTAConfiguration;

@Configuration
@DependsOn(JTAConfiguration.TRANSACTION_MGR_NAME)
@EnableJpaRepositories(basePackageClasses = SecondDAO.class, entityManagerFactoryRef = "secondEntityManager", transactionManagerRef = "secondTransactionManager")
public class SecondConfiguration {


	@Bean
	@DependsOn("secondDataSource")
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
		//TODO: This has been put here for enabling startup. Be sure of if connections are closing.
		xaDataSource.setMaxPoolSize(100);
		return xaDataSource;
	}

	@StandaloneProfile
	@Bean
	public PlatformTransactionManager secondTransactionManager(LocalContainerEntityManagerFactoryBean entityManager) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManager.getObject());
		return transactionManager;
	}

}
