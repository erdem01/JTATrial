package erdemc;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JtaExecuterApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder().sources(JtaExecuterApplication.class)
				.bannerMode(Banner.Mode.OFF).run(args);

		JtaExecuterApplication app = context.getBean(JtaExecuterApplication.class);
		app.start();
	}

	private void start() {
		System.out.println("Hello World!");
	}
}
