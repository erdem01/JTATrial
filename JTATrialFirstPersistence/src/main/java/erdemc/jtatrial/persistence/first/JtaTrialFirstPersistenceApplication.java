package erdemc.jtatrial.persistence.first;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import erdemc.jtatrial.txn.JtaTrialTransactionManagerApplication;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
@Import({JtaTrialTransactionManagerApplication.class})
public class JtaTrialFirstPersistenceApplication {
}
