package db.client.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket apiConfDirect() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("mongo")
				.genericModelSubstitutes(ResponseEntity.class)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.any())
				.build();
	}

	protected ApiInfo apiInfo() {
		return new ApiInfo(
				"Alternative db client solutions",
				"Adopting sql query to nosql request and retrieve data",
				"0.01",
				"",
				new Contact("Alex Bezugliy", "", "bezugliy.alex@gmail.com"),
				"",
				""
		);
	}

}
