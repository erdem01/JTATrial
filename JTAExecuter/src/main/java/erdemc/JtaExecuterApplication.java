package erdemc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import erdemc.dao.first.FirstDAO;
import erdemc.dao.second.SecondDAO;

@SpringBootApplication
@EnableAutoConfiguration(exclude=HibernateJpaAutoConfiguration.class)
public class JtaExecuterApplication {
	
	@Autowired
	private FirstDAO firstDAO;

	@Autowired
	private SecondDAO secondDAO;
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder().sources(JtaExecuterApplication.class)
				.bannerMode(Banner.Mode.OFF).run(args);

		JtaExecuterApplication app = context.getBean(JtaExecuterApplication.class);
		app.start();
	}

	private void start() {
		firstDAO.findAll();
		secondDAO.findAll();
		System.out.println("Hello World!");
	}
}
