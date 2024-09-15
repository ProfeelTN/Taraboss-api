package profeel.taraboss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages ={
		"profeel.taraboss.Controller",
		"profeel.taraboss.Service",
		"profeel.taraboss.Repository",
		"profeel.taraboss.Auth",
		"profeel.taraboss.Config"


})
@EnableJpaRepositories(basePackages = {"profeel.taraboss.Repository"})
@EntityScan(basePackages = {"profeel.taraboss.Entity"})
@EnableAspectJAutoProxy
public class TarabossApplication {

	public static void main(String[] args) {
		SpringApplication.run(TarabossApplication.class, args);
	}

}
