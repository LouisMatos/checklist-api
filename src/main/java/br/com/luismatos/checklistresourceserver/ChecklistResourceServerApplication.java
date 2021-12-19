package br.com.luismatos.checklistresourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class ChecklistResourceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChecklistResourceServerApplication.class, args);
	}

	@Bean
	@Profile("local")
	public WebMvcConfigurer corsLocalConfig() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200")
						.allowedMethods("GET", "PUT", "POST", "DELETE","PATCH").maxAge(900)
						.allowedHeaders("Origin", "X-requested-With", "Content-Type", "Accept", "Authorization");
			}
		};
	}
	
	@Bean
	@Profile("aws")
	public WebMvcConfigurer corsAwsConfig() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://estudo-api-spa.s3-website-us-east-1.amazonaws.com")
						.allowedMethods("GET", "PUT", "POST", "DELETE").maxAge(900)
						.allowedHeaders("Origin", "X-requested-With", "Content-Type", "Accept", "Authorization");
			}
		};
	}

	@Bean
	public OpenAPI customOpenApi() {
		return new OpenAPI()
				.info(new Info().title("Checklist api do curso da udemy").description("Api de estudos de springboot")
						.contact(new Contact().name("Luis Matos").email("luismatos@teste.com.br")).version("V1")
						.termsOfService("https://teste.com.br").license(new License().name("Apache 2.0")));
	}

}
