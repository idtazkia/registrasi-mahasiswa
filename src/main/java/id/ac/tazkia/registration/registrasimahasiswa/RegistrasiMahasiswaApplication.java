package id.ac.tazkia.registration.registrasimahasiswa;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EntityScan(
		basePackageClasses = {RegistrasiMahasiswaApplication.class, Jsr310JpaConverters.class}
)
public class RegistrasiMahasiswaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrasiMahasiswaApplication.class, args);
	}

	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}
	@Bean
	public SpringDataDialect springDataDialect() {
		return new SpringDataDialect();
	}
}
