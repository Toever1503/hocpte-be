package hocpte.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
public class SwaggerConfiguration {
    //Set
    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("public-api")
                .apiInfo(apiInfo()).select().paths(postPaths()).build();
    }

    private Predicate<String> postPaths() {
        return regex("/.*");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("cy API")
                .description("cy API reference for developers")
                .termsOfServiceUrl("http://")
                .contact(new Contact("haunv", "https://www/", ".")).license("")
                .licenseUrl("not have").version("1.0").build();
    }
}
