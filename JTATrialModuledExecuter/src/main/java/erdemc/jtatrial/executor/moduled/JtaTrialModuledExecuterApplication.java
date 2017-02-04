package erdemc.jtatrial.executor.moduled;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import erdemc.jtatrial.persistence.first.dao.FirstDAO;
import erdemc.jtatrial.persistence.first.model.First;
import erdemc.jtatrial.persistence.second.dao.SecondDAO;
import erdemc.jtatrial.persistence.second.model.Second;

@SpringBootApplication
@EnableAutoConfiguration(exclude={HibernateJpaAutoConfiguration.class, JtaAutoConfiguration.class})
public class JtaTrialModuledExecuterApplication {

	@Autowired
	private FirstDAO firstDAO;

	@Autowired
	private SecondDAO secondDAO;
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder().sources(JtaTrialModuledExecuterApplication.class)
				.bannerMode(Banner.Mode.OFF).run(args);

		JtaTrialModuledExecuterApplication app = context.getBean(JtaTrialModuledExecuterApplication.class);
		app.start();
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
