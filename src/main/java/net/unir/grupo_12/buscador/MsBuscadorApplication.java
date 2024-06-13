package net.unir.grupo_12.buscador;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@OpenAPIDefinition(
		info = @Info(
				title = "MS-BUSCADOR",
				version = "0.0.1",
				description = "Servicio para gestionar libros."
		)
)
@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "net.unir.grupo_12.buscador.repository")
@EntityScan(basePackages = "net.unir.grupo_12.buscador.entity")
public class MsBuscadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBuscadorApplication.class, args);
	}

}
