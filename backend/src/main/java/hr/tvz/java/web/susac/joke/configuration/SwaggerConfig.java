package hr.tvz.java.web.susac.joke.configuration;

import com.fasterxml.classmate.TypeResolver;
import hr.tvz.java.web.susac.joke.dto.CategoryDTO;
import hr.tvz.java.web.susac.joke.dto.rating.RatingDisplayDTO;
import hr.tvz.java.web.susac.joke.dto.user.UserDTO;
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
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
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
                .securitySchemes(Arrays.asList(apiKey()))
                .additionalModels(typeResolver.resolve(RatingDisplayDTO.class),
                        typeResolver.resolve(UserDTO.class),
                        typeResolver.resolve(CategoryDTO.class))
                .ignoredParameterTypes(ModelAndView.class, View.class, BasicErrorController.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Joke Rest App",
                "Description of Joke REST App. \n" +
                        "For user authorization, please use the following: \n" +
                        "ADMIN - Username: admin, password: admin \n" +
                        "USER - Username: userone, password: userone",
                "Version 1.0",
                "Terms of service",
                new Contact("Mario Sušac", "www.example.com", "msusac@tvz.hr"),
                "License of API", "API license URL", Collections.emptyList());
    }

    private ApiKey apiKey() {
        return new ApiKey("jwtToken", "Authorization", "header");
    }
}
