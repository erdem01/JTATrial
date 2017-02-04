package erdemc.jtatrial.persistence.second;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import erdemc.jtatrial.txn.JtaTrialTransactionManagerApplication;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
@Import({JtaTrialTransactionManagerApplication.class})
public class JtaTrialSecondPersistenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JtaTrialSecondPersistenceApplication.class, args);
	}
}
