package erdemc.jtatrial.executor.moduled;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import erdemc.jtatrial.persistence.first.JtaTrialFirstPersistenceApplication;
import erdemc.jtatrial.persistence.second.JtaTrialSecondPersistenceApplication;
import erdemc.jtatrial.txn.JtaTrialTransactionManagerApplication;

@SpringBootApplication
@EnableAutoConfiguration(exclude={HibernateJpaAutoConfiguration.class, JtaAutoConfiguration.class})
@Import({JtaTrialTransactionManagerApplication.class
	, JtaTrialSecondPersistenceApplication.class
	, JtaTrialFirstPersistenceApplication.class})
public class JtaTrialModuledExecuterApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(JtaTrialModuledExecuterApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(JtaTrialModuledExecuterApplication.class);
	}

}
