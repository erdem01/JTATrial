package erdemc.jtatrial.executor.moduled;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import erdemc.jtatrial.persistence.first.JtaTrialFirstPersistenceApplication;
import erdemc.jtatrial.persistence.first.dao.FirstDAO;
import erdemc.jtatrial.persistence.first.model.First;
import erdemc.jtatrial.persistence.second.JtaTrialSecondPersistenceApplication;
import erdemc.jtatrial.persistence.second.dao.SecondDAO;
import erdemc.jtatrial.persistence.second.model.Second;
import erdemc.jtatrial.txn.JtaTrialTransactionManagerApplication;

@SpringBootApplication
@EnableAutoConfiguration(exclude={HibernateJpaAutoConfiguration.class, JtaAutoConfiguration.class})
@Import({JtaTrialTransactionManagerApplication.class, JtaTrialSecondPersistenceApplication.class, JtaTrialFirstPersistenceApplication.class})
public class JtaTrialModuledExecuterApplication extends SpringBootServletInitializer {

	@Autowired
	private FirstDAO firstDAO;

	@Autowired
	private SecondDAO secondDAO;
	
	public static void main(String[] args) {
		SpringApplication.run(JtaTrialModuledExecuterApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(JtaTrialModuledExecuterApplication.class);
	}

	@Transactional
	private void start() {
		First first = new First();
		first.setText("first");
		Second second = new Second();
		second.setText("second");
		firstDAO.save(first);
		secondDAO.save(second);
		System.out.println("Hello World!");
		throw new RuntimeException();
	}
}
