package hr.tvz.java.web.susac.joke.configuration;

import com.fasterxml.classmate.TypeResolver;
import hr.tvz.java.web.susac.joke.dto.CategoryDTO;
import hr.tvz.java.web.susac.joke.dto.CategorySearchDTO;
import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.JokeSearchDTO;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@AllArgsConstructor
@EnableSwagger2
public class SwaggerConfig {

    private final TypeResolver typeResolver;

    @Bean
    public Docket productApi(){
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .pathMapping("/")
                .apiInfo(apiInfo())
                .additionalModels(
                        typeResolver.resolve(CategoryDTO.class),
                        typeResolver.resolve(CategorySearchDTO.class),
                        typeResolver.resolve(JokeDTO.class),
                        typeResolver.resolve(JokeSearchDTO.class)
                )
                .ignoredParameterTypes(ModelAndView.class, View.class, BasicErrorController.class);

    }

    private ApiInfo apiInfo(){
        final String title = "Joke Rest API";
        final String description = "Swagger Fox Documentation of Joke Rest API";
        final String version = "Version 1.0";
        final String termsOfServiceUrl = "Terms of Service";
        final String license = "License of API";
        final String licenseUrl = "API License URL";

        final String contactName = "Mario Sušac";
        final String contactUrl = "https://github.com/msusac/joke-rest-api";
        final String contactEmail = "msusac@tvz.hr";
        final Contact contact = new Contact(contactName, contactUrl, contactEmail);

        return new ApiInfo(
                title, description, version, termsOfServiceUrl,
                contact, license, licenseUrl, Collections.emptyList()
        );
    }
}
