package erdemc;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import com.at.mul.MulAtAppConfiguration;
import com.at.mul.domain.customer.Customer;
import com.at.mul.domain.order.Order;
import com.at.mul.repository.customer.CustomerRepository;
import com.at.mul.repository.order.OrderRepository;

@SpringBootApplication
@EnableAutoConfiguration(exclude={HibernateJpaAutoConfiguration.class, JtaAutoConfiguration.class})
@Import(MulAtAppConfiguration.class)
public class JtaExecuterForMulAtApplication {

	@Autowired
	private CustomerRepository firstDAO;

	@Autowired
	private OrderRepository secondDAO;
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder().sources(JtaExecuterForMulAtApplication.class)
				.bannerMode(Banner.Mode.OFF).run(args);

		JtaExecuterForMulAtApplication app = context.getBean(JtaExecuterForMulAtApplication.class);
		app.start();
	}

	@Transactional
	private void start() {
		Customer first = new Customer();
		first.setName("customer");
		Order second = new Order();
		second.setCode(22);
		firstDAO.save(first);
		secondDAO.save(second);
		System.out.println("Hello World!");
		throw new RuntimeException();
	}
}
