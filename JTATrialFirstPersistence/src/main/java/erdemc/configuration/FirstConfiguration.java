package erdemc.configuration;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

import erdemc.dao.first.FirstDAO;
import erdemc.model.first.First;

@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackageClasses = FirstDAO.class, entityManagerFactoryRef = "firstEntityManager", transactionManagerRef = "firstTransactionManager")
public class FirstConfiguration {

	@Primary
	@Bean
	@DependsOn("transactionManager")
	public LocalContainerEntityManagerFactoryBean firstEntityManager(JpaVendorAdapter vendorAdapter) {
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");
		
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setJtaDataSource(firstDataSource());
		entityManager.setJpaVendorAdapter(vendorAdapter);
		entityManager.setPackagesToScan(First.class.getPackage().getName());
		entityManager.setPersistenceUnitName("firstPersistenceUnit");
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}

	@Primary
	@Bean(initMethod = "init", destroyMethod = "close")
	public DataSource firstDataSource() {
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl("jdbc:mysql://localhost:3306/jtatrial");
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setPassword("invader84;");
		mysqlXaDataSource.setUser("erdemc");
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("xads1");
		return xaDataSource;
	}

//	@Primary
//	@Bean
//	public PlatformTransactionManager firstTransactionManager() {
//		JpaTransactionManager transactionManager = new JpaTransactionManager();
//		transactionManager.setEntityManagerFactory(firstEntityManager().getObject());
//		return transactionManager;
//	}

}
